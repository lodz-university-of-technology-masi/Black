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

  isLoggedIn: boolean;
  role: string;
  userService: UserService;
  title = 'frontend';

  constructor(userService: UserService,
              private router: Router,
              private toastr: ToastrService) {
    this.userService = userService;

  }
  async ngOnInit() {
    this.isLoggedIn = await this.userService.isLoggedIn();
    this.role = this.userService.role;
  }

  async logout() {
    await this.userService.logout();
    await this.router.navigate(['/login']);
    this.toastr.info('Zostałeś wylogowany', 'Sukces', {
      timeOut: 3000,
      closeButton: true,
    });
  }


}
