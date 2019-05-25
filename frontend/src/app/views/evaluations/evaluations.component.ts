import {Component, OnInit} from '@angular/core';
import {Evaluation, Test, TestAnswer, User} from "../../model/entities";
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {EvaluationService} from "../../services/evaluation.service";

@Component({
  selector: 'app-tests',
  templateUrl: './evaluations.component.html',
})
export class EvaluationsComponent implements OnInit {
  evaluations: Evaluation[];

  constructor(private evaluationService: EvaluationService,
              private router: Router,
              public userService: UserService) {
  }

  ngOnInit() {
    this.loadEvaluations();
  }

  async loadEvaluations() {
    this.evaluations = await this.evaluationService.getAll();
    this.evaluations.forEach(e => {
      e.pointsSum = this.getPoints(e);
    })
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
}
