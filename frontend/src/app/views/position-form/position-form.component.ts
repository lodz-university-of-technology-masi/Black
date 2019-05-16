import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PositionService} from '../../services/position.service';
import {Position} from '../../model/entities';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-position-form',
  templateUrl: './position-form.component.html',
  styleUrls: ['./position-form.component.css']
})
export class PositionFormComponent implements OnInit {

  id: number;
  positionToEdit: Position;
  editPositionGroup: FormGroup;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private toastr: ToastrService,
              private positionService: PositionService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params.id;
    this.positionService.getOne(this.id).then((item) => {
      this.positionToEdit = item;

      this.editPositionGroup = new FormGroup({
        id: new FormControl(this.positionToEdit.id),
        name: new FormControl(this.positionToEdit.name, Validators.required),
        description: new FormControl(this.positionToEdit.description, Validators.required),
        active: new FormControl(this.positionToEdit.active, Validators.required)
      });
    });
  }

  editPosition() {
    this.positionService.update(this.editPositionGroup.value).then(() => {
      this.router.navigateByUrl('positions');
      this.toastr.success('Stanowisko zostało zedytowane', 'Sukces', {
        timeOut: 3000,
        closeButton: true,
      });
    });
  }

  cancelEditPosition() {
    this.router.navigateByUrl('positions');
    this.toastr.info('Edycja stanowiska została anulowana', 'Informacja', {
      timeOut: 3000,
      closeButton: true,
    });
  }

}
