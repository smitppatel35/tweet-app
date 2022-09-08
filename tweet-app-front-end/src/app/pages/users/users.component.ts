import { Component, OnInit } from '@angular/core';
import {User} from "../../models/User";
import {TweetService} from "../../services/tweet/tweet.service";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  userList: User[] = [];

  constructor(private _userService: UserService, private router: Router, private cookieService: CookieService) {
    
  }

  ngOnInit(): void {
    this._userService.getAllUserProfiles().subscribe(data => {
      this.userList = data;
    })
  }

}
