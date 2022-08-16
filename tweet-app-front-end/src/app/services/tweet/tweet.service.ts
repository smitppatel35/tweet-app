import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiPaths} from "../ApiPaths";
import {Tweet} from "../../models/Tweet";
import {catchError, map, Observable, Subject, throwError} from "rxjs";
import {environment} from "../../../environments/environment";
import {CookieService} from "ngx-cookie-service";

class TweetRequest {
  tweet: string;


  constructor(tweet: string) {
    this.tweet = tweet;
  }
}

@Injectable({
  providedIn: 'root'
})
export class TweetService {

  private API_URL = environment.API_URL;

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  // tweet CRUD

  save(message: string){
    let username = this.cookieService.get("username");
    this.http.post(this.API_URL + ApiPaths.TWEET_BASE_PATH + username + "/add", new TweetRequest(message)).subscribe();
  }

  updateTweet(id: string, message: string) {
    let username = this.cookieService.get("username");
    return this.http.put(this.API_URL + ApiPaths.TWEET_BASE_PATH + username + "/update/" + id, new TweetRequest(message));
  }

  getAllTweet(): Observable<Tweet[]>{
    // @ts-ignore
    return this.http.get(this.API_URL + ApiPaths.API_GET_ALL_TWEETS)
      .pipe(
        map((data: Tweet[]) => {
          return data;
        }), catchError(err => {
          return throwError('Api Call Failed. cause: ' + err);
        })
    );
  }

  likeTweet(id: string) {
    let username = this.cookieService.get("username");
    this.http.put(this.API_URL + ApiPaths.TWEET_BASE_PATH + username + "/like/"+id, null).subscribe();
  }

  replyTweet(id: string, message: string){
    let username = this.cookieService.get("username");
    this.http.post(this.API_URL + ApiPaths.TWEET_BASE_PATH + username + "/reply/" + id, new TweetRequest(message)).subscribe();
  }

}
