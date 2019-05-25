import {Component, OnInit} from '@angular/core';
import {TestAnswer} from '../../model/entities';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {TestAnswerService} from '../../services/test-answer.service';

@Component({
  selector: 'app-tests',
  templateUrl: './answers.component.html',
  styleUrls: ['./answers.component.css']
})
export class AnswersComponent implements OnInit {
  testAnswers: TestAnswer[];

  constructor(private testAnswerService: TestAnswerService,
              private router: Router,
              public userService: UserService) {
  }

  ngOnInit() {
    this.loadTestAnswers();
  }

  async loadTestAnswers() {
    this.testAnswers = await this.testAnswerService.getAll();
  }

  onReviewTestAnswer(testAnswer: TestAnswer) {
    this.router.navigate(['/evaluations', 'new'], {queryParams: {ans: testAnswer.id}});
  }
}
