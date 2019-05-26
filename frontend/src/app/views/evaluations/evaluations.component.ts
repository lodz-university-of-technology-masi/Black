import {Component, HostListener, OnInit} from '@angular/core';
import {Evaluation} from '../../model/entities';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {EvaluationService} from '../../services/evaluation.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-tests',
  templateUrl: './evaluations.component.html',
  styleUrls: ['./evaluations.component.css']
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

  async sendEmail(evaluation: Evaluation) {
    await this.evaluationService.sendEmailNotification(evaluation);
    this.toastr.success('Powiadomienie email zostało wysłane', 'Wysłano', {
      timeOut: 3000,
      closeButton: true,
    });
  }
}
