import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {PositionService} from '../../services/position.service';
import {Position} from '../../model/entities';

@Component({
  selector: 'app-positions',
  templateUrl: './positions.component.html',
  styleUrls: ['./positions.component.css']
})
export class PositionsComponent implements OnInit {

  positions: Position[];

  constructor(private positionsService: PositionService,
              private router: Router) { }

  ngOnInit() {
    this.loadPositions();
  }


  async loadPositions() {

    this.positions = await this.positionsService.getAll<Position>().toPromise()
    console.log(this.positions);
  }

}
