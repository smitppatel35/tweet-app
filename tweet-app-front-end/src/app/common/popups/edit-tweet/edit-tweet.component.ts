import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {TweetService} from "../../../services/tweet/tweet.service";

@Component({
  selector: 'app-edit-tweet',
  templateUrl: './edit-tweet.component.html',
  styleUrls: ['./edit-tweet.component.css']
})
export class EditTweetComponent implements OnInit {
  @Input() tweet;
  @Input() tweetId;

  tweetUpdated = '';

  constructor(public activeModal: NgbActiveModal, private _tweetService: TweetService) {}

  ngOnInit(): void {
    this.tweetUpdated = this.tweet;
  }

  submit(){
    this._tweetService.updateTweet(this.tweetId, this.tweetUpdated).subscribe(res => {
      console.log(res);
    }, error => {
      console.log(error);
    });

    // console.log(this.tweetUpdated);
    this.activeModal.close();
  }
}
