export class User{
  firstName: string;
  lastName: string;
  email: string;
  username: string;
  contactNumber: string;
  gender: string;
  avatar: string;

  constructor(firstName: string, lastName: string, email: string, username: string, contactNumber: string, gender: string, avatar: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.username = username;
    this.contactNumber = contactNumber;
    this.gender = gender;
    this.avatar = avatar;
  }
}
