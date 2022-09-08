import { Component, OnInit } from '@angular/core';
import {User} from "../../models/User";
import {UserService} from "../../services/user/user.service";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  query = '';
  userList: User[] = [];

  constructor(private _userService: UserService, private router: Router, private cookieService: CookieService) {
   
  }

  ngOnInit(): void {
    this._userService.getAllUserProfiles().subscribe(data => {
      this.userList = data;
    })
  }

  search(){
    this._userService.profile(this.query).subscribe(data => {
      this.userList = data;
    })
  }

}
