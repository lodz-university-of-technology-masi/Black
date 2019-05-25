import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  title = 'frontend';

  constructor(public userService: UserService,
              private router: Router,
              private toastr: ToastrService) {
    this.userService = userService;

  }
  async ngOnInit() {
  }

  async logout() {
    await this.userService.logout();
    await this.router.navigate(['/login']);
    this.toastr.info('Zostałeś wylogowany', 'Sukces', {
      timeOut: 3000,
      closeButton: true,
    });
  }
  logoUrl(){
    if (this.userService.loggedIn) {
      return '/tests'
    } else {
      return '/login'
    }
  }

}
