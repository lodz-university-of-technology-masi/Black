export class RegisterCredential {
  login: string;
  password: string;
  email: string;
  language: string;

  constructor(login: string, password: string, email: string, language: string) {
    this.login = login;
    this.password = password;
    this.email = email;
    this.language = language;
  }
}

