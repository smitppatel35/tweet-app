import { Component, OnInit } from '@angular/core';
import {Tweet} from "../../models/Tweet";
import {TweetService} from "../../services/tweet/tweet.service";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-tweets',
  templateUrl: './tweets.component.html',
  styleUrls: ['./tweets.component.css']
})
export class TweetsComponent implements OnInit {

  tweetList: Tweet[] = [];

  constructor(private _tweetService: TweetService, private router: Router, private cookieService: CookieService) {
    let username = this.cookieService.get("username");
    if( username.trim().length === 0 || username === '' && username === undefined) {
      this.router.navigate(['/auth/login']);
    }
  }

  ngOnInit(): void {
    this.reloadTweets();
  }


  refreshComponent(){
    window.location.reload();
  }


  reloadTweets(){
    this._tweetService.getAllTweet().subscribe(data => {
      this.tweetList = data;
    });
  }
}
