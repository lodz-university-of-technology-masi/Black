import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {PositionService} from '../../services/position.service';
import {Position} from '../../model/entities';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-positions',
  templateUrl: './positions.component.html',
  styleUrls: ['./positions.component.css']
})
export class PositionsComponent implements OnInit {

  positions: Position[];
  private position: Position;
  addPositionGroup: FormGroup;

  constructor(private positionsService: PositionService,
              private router: Router,
              private toastr: ToastrService) {

    this.addPositionGroup = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      active: new FormControl('', Validators.required)
    });
  }

  ngOnInit() {
    this.loadPositions();
  }


  async loadPositions() {

    this.positions = await this.positionsService.getAll<Position>().toPromise()
    console.log(this.positions);
  }

  async addPosition() {

    const position: Position = {
      id: null,
      name: this.addPositionGroup.get('name').value,
      description: this.addPositionGroup.get('description').value,
      active: this.addPositionGroup.get('active').value === 'true'
    };
    await this.positionsService.create(position).toPromise();
    await this.loadPositions();

    this.toastr.success('Stanowisko zostało dodane', 'Sukces', {
      timeOut: 3000,
      closeButton: true,
    });

  }
}
