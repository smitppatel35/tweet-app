export class Reply {
  replyId: number;
  userId: number;
  username: string;
  firstName: string;
  lastName: string;
  reply: string;
  avatar: string;
  likes: string;
  edited: string;
  timestamp: string;

  constructor(replyId: number, userId: number, username: string, firstName: string, lastName: string, reply: string, avatar: string, likes: string, edited: string, timestamp: string) {
    this.replyId = replyId;
    this.userId = userId;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.reply = reply;
    this.avatar = avatar;
    this.likes = likes;
    this.edited = edited;
    this.timestamp = timestamp;
  }
}
