import {Component, HostListener, OnInit} from '@angular/core';
import {Evaluation} from '../../model/entities';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {EvaluationService} from '../../services/evaluation.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-tests',
  templateUrl: './evaluations.component.html',
})
export class EvaluationsComponent implements OnInit {
  evaluations: Evaluation[];

  constructor(private evaluationService: EvaluationService,
              private router: Router,
              public userService: UserService,
              private toastr: ToastrService) {
  }

  ngOnInit() {
    this.loadEvaluations();
  }

  async loadEvaluations() {
    this.evaluations = await this.evaluationService.getAll();
    this.evaluations.forEach(e => {
      e.pointsSum = this.getPoints(e);
    });
  }

  onShowEvaluation(evaluation: Evaluation) {
    this.router.navigate(['/evaluations', evaluation.id]);
  }

  getPoints(evaluation: Evaluation): number {
    let sum = 0;
    for (const e of evaluation.answersEvaluations) {
      if (e.points) {
        sum += e.points;
      }
    }
    return sum;
  }

  @HostListener('document:keyup', ['$event'])
  onKeyUp(event: KeyboardEvent) {
    if (event.key === 'PrintScreen') {
      this.copyToClipboard();
      event.preventDefault();
    }
  }

  @HostListener('document:contextmenu', ['$event'])
  onClick1($event) {
    event.preventDefault();
  }

  @HostListener('mouseup', ['$event']) onClick($event) {

    if ($event.which === 3) {
      this.toastr.error('Nie wolno klikać prawym przyciskiem myszy!', 'Drogi kandydacie', {
        timeOut: 3000,
        closeButton: true,
      });
      $event.preventDefault();
    }
  }


  copyToClipboard() {
    const aux = document.createElement('input');
    aux.setAttribute('value', 'print screen disabled!');
    document.body.appendChild(aux);
    aux.select();
    document.execCommand('copy');
    document.body.removeChild(aux);
    this.toastr.error('Nie wolno robić print screen!', 'Drogi kandydacie', {
      timeOut: 3000,
      closeButton: true,
    });
  }
}
