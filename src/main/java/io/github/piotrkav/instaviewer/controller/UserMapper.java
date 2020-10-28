package io.github.piotrkav.instaviewer.controller;

import io.github.piotrkav.instaviewer.model.UserInfo;
import me.postaddict.instagram.scraper.model.Account;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  private final ModelMapper modelMapper;

  public UserMapper() {
    this.modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

    PropertyMap<Account, UserInfo> orderMap = new PropertyMap<>() {
      protected void configure() {
        map().setId(String.valueOf(source.getId()));
        map().setLogin(source.getUsername());
      }
    };

    modelMapper.addMappings(orderMap);
  }

  public UserInfo mapAccountToUser(Account account) {
    return this.modelMapper.map(account, UserInfo.class);
  }
}
