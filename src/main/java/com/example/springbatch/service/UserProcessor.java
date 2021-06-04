package com.example.springbatch.service;

import com.example.springbatch.dto.UserDto;
import com.example.springbatch.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UserProcessor implements ItemProcessor<UserDto, User> {

    private static final String DATE_ORDINAL_SUFFIX = "(?<=\\d)(st|nd|rd|th)";
    private static final String DATE_ORDINAL_SUFFIX_MATCHER = "(.*th.*|.*st.*|.*rd.*|.*nd.*)";

    private final String dmyFormat;
    private final String mdyFormat;

    public UserProcessor(@Value("${dmy}") String dmy,
                         @Value("${mdy}") String mdy) {
        this.dmyFormat = dmy;
        this.mdyFormat = mdy;
    }

    @Override
    public User process(@NonNull UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setDate(parseDate(userDto.getDate()));
        return user;
    }

    private LocalDate parseDate(String date) {
        if (date.matches(DATE_ORDINAL_SUFFIX_MATCHER)) {
            String correctDate = date.replaceAll(DATE_ORDINAL_SUFFIX, "");
            return LocalDate.parse(correctDate, DateTimeFormatter.ofPattern(mdyFormat));
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(dmyFormat));
    }
}