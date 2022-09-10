import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Tweet} from "../../models/Tweet";
import {TweetService} from "../../services/tweet/tweet.service";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  isAuthenticated: boolean = true;
  tweet = '';

  tweetList: Tweet[] = [];

  constructor(private _tweetService: TweetService, private router: Router, private cookieService: CookieService) {
    // let username = this.cookieService.get("username");
    // if( username.trim().length === 0 || username === '' && username === undefined) {
    //   this.router.navigate(['/auth/login']);
    // }
  }

  ngOnInit(): void {
    this.reloadTweets();
  }

  post(){
    this._tweetService.save(this.tweet).subscribe(res => {
      this.tweet='';
      this.refreshComponent();
    }, error => {
      console.log(error);
    });
    
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
