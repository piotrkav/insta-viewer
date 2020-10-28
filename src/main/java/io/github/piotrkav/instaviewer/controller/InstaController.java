package io.github.piotrkav.instaviewer.controller;

import io.github.piotrkav.instaviewer.api.UserApi;
import io.github.piotrkav.instaviewer.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class InstaController implements UserApi {

  private final Instagram instagram;
  private final UserMapper userMapper;

  @SneakyThrows
  @Override
  public ResponseEntity<UserInfo> usersLoginGet(String login) {
    if (StringUtils.isEmpty(login)) {
      return ResponseEntity.badRequest().build();
    }

    Optional<Account> igAccount = Optional.ofNullable(instagram.getAccountByUsername(login));
    return igAccount.map(account -> ResponseEntity.ok(userMapper.mapAccountToUser(account))).orElse(ResponseEntity.notFound().build());
  }

  @SneakyThrows
  @GetMapping("/users/{login}/account")
  public ResponseEntity<Account> getAccount(@PathVariable(name = "login") String login) {
    if (StringUtils.isEmpty(login)) {
      return ResponseEntity.badRequest().build();
    }

    Optional<Account> igAccount = Optional.ofNullable(instagram.getAccountByUsername(login));
    return igAccount.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}
