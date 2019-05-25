import {Injectable} from '@angular/core';
import {Credentials} from '../model/Credentials';
import {HttpClient} from '@angular/common/http';
import {Role, User} from '../model/entities';
import {BaseEntityService} from './base-entity.service';
import {Router} from '@angular/router';
import {RegisterCredential} from '../model/RegisterCredential';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseEntityService<User> {

  private static BASE_USER_URL = 'users';
  private static CURRENT_USER_URL = `${UserService.BASE_USER_URL}/current`;

  private static SIGNOUT_URL = 'user/signout';
  private static SIGNIN_URL = 'user/signin';
  private static REGISTER_URL = 'register';

  private currentUser: User;

  private _loggedIn = false;
  role = '';

  constructor(http: HttpClient, private router: Router) {
    super(http);
  }

  async getRedactors(): Promise<User[]> {
    return this.http.get<User[]>(UserService.BASE_USER_URL + '/redactors').toPromise();
  }

  async login(credentials: Credentials): Promise<User> {
    const formData = new FormData();
    formData.append('username', credentials.username);
    formData.append('password', credentials.password);

    return this.http.post<void>(UserService.SIGNIN_URL, formData)
      .toPromise()
      .then(() => this.loadCurrentUser())
      .then((user) => {
        this._loggedIn = true;
        this.role = user.role;

        return user;
      });
  }

  createNewAccount(registerCredentials: RegisterCredential): Observable<any> {
    return this.http.post<Observable<any>>(UserService.REGISTER_URL, registerCredentials);
  }

  createNewRedactorAccount(registerCredentials: RegisterCredential): Observable<any> {
    return this.http.post<Observable<any>>(UserService.REGISTER_URL + '/redactor', registerCredentials);
  }

  async logout(): Promise<void> {
    await this.http.post<void>(UserService.SIGNOUT_URL, {}).toPromise();
    this._loggedIn = false;
    this.currentUser = null;
    return;
  }

  getEntityUrl(): string {
    return UserService.BASE_USER_URL;
  }

  private async loadCurrentUser(): Promise<User> {
    return this.http.get<User>(UserService.CURRENT_USER_URL).toPromise()
      .then(user => {
        this.currentUser = user;
        this._loggedIn = true;
        return user
      });
  }

  getCurrentUser(): User {
    return this.currentUser;
  }
  get loggedIn(): boolean {
    return this._loggedIn
  }

  async checkLoggedIn(): Promise<boolean> {
    if (this._loggedIn) {
      return this._loggedIn;
    }
    this._loggedIn = await this.loadCurrentUser()
      .then((user) => true)
      .catch((err) => false)
    return this._loggedIn;
  }

  isModeratorOrRedactor(): boolean {
      return this.currentUser && (this.currentUser.role === Role.MODERATOR || this.currentUser.role === Role.REDACTOR)
  }

  isModerator(): boolean {
    return this.currentUser && this.currentUser.role === Role.MODERATOR
  }

  isRedactor(): boolean {
    return this.currentUser && this.currentUser.role === Role.REDACTOR
  }

  isCandidate(): boolean {
    return this.currentUser && this.currentUser.role == Role.CANDIDATE
  }
}
