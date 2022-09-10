import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {faHeart, faTrash} from "@fortawesome/free-solid-svg-icons";
import { faReply } from '@fortawesome/free-solid-svg-icons';
import {Tweet} from "../../models/Tweet";
import {TweetService} from "../../services/tweet/tweet.service";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {EditTweetComponent} from "../popups/edit-tweet/edit-tweet.component";
import { faEdit } from '@fortawesome/free-regular-svg-icons';
import {ReplyTweetComponent} from "../popups/reply-tweet/reply-tweet.component";
import {CookieService} from "ngx-cookie-service";
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-tweet-card',
  templateUrl: './tweet-card.component.html',
  styleUrls: ['./tweet-card.component.css']
})
export class TweetCardComponent implements OnInit {
  @Output() refreshEvent = new EventEmitter<string>();
  @Input("tweet") tweet: Tweet = new Tweet("null", "null", "null", "null", "null", "null", "null", [], "null");

  faLike = faHeart;
  faReply = faReply;
  faEdit = faEdit;
  faDelete = faTrash;

  reply: string = '';

  username = this.cookieService.get("username");

  constructor(
    private _tweetService: TweetService, 
    private modalService: NgbModal, 
    private cookieService: CookieService,
    public oidcSecurityService: OidcSecurityService) { 
      oidcSecurityService.checkAuth().subscribe(({userData}) => {
        this.username = userData['email'].split("@")[0];
      });
  }

  ngOnInit(): void {
  }

  callParentToRefresh() {
    this.refreshEvent.emit('refresh');
  }

  openEditModal(){
    const modelRef = this.modalService.open(EditTweetComponent);
    modelRef.componentInstance.tweet = this.tweet.tweet;
    modelRef.componentInstance.tweetId = this.tweet.tweetId;

    modelRef.result.then(res => {
      this.refreshEvent.emit('refresh');
    }).catch((res) => {
      console.log(res);
    });
  }

  openReplyModal() {
    const modelRef = this.modalService.open(ReplyTweetComponent);
    modelRef.componentInstance.replyList = this.tweet.reply;
    modelRef.componentInstance.tweetId = this.tweet.tweetId;

    modelRef.result.then(res => {
      this.refreshEvent.emit('refresh');
      console.log(res);
    }).catch((res) => {
      console.log(res);
    });

  }

  like(id: string){
    this._tweetService.likeTweet(id).subscribe(res => {
      this.callParentToRefresh();
    }, error => {
      console.log(error['error']['message']);
    });
    
  }

  replyTweet(tweetId) {
    this._tweetService.replyTweet(tweetId, this.reply).subscribe(res => {
      this.callParentToRefresh();
    }, error => {
      console.log(error['error']['message']);
    });
  }

  deleteTweet(id: string) {
    this._tweetService.delete(id).subscribe(res => {
      this.callParentToRefresh();
    }, error => {
      console.log(error['error']['message']);
    });
  }
}
