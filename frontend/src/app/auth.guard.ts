import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {UserService} from "./services/user.service";
import {defer, from, Observable} from "rxjs";


@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> {
    let url: string = state.url;

    return from(this.checkLogin(url));
  }

  async checkLogin(url: string): Promise<boolean> {
    if (await this.userService.isLoggedIn()) {
      return true;
    }

    if (url.startsWith('/login') || url.startsWith('/register')) {
      return true
    }
    // Navigate to the login page with extras
    this.router.navigate(['/login']);
    return false;
  }
}
