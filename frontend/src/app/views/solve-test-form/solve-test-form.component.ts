import {Component, OnInit} from '@angular/core';
import {QuestionAnswer, Test, TestAnswer} from "../../model/entities";
import {ActivatedRoute, Router} from "@angular/router";
import {TestService} from "../../services/test.service";
import {TestAnswerService} from "../../services/test-answer.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-solve-test-form',
  templateUrl: './solve-test-form.component.html',
  styleUrls: ['./solve-test-form.component.css']
})
export class SolveTestFormComponent implements OnInit {

  test: Test;
  private answers: QuestionAnswer[] = [];

  constructor(private testService: TestService,
              private router: Router,
              private userService: UserService,
              private testAnswerService: TestAnswerService,
              private route: ActivatedRoute) {
  }

  async ngOnInit() {
    await this.reloadComponent()
  }

  async reloadComponent() {
    console.log('init!');
    let id = this.route.snapshot.paramMap.get('id');
    this.answers=[];
    this.test = await this.testService.getOne(Number.parseInt(id));

    for(const question of this.test.questions){
      let answer: QuestionAnswer = {
        id: null,
        type: question.type,
        choiceAnswer: []
      };
      this.answers.push(answer);
    }
  }

  async onSave() {
    for(const answer of this.answers) {
      if (answer.type === "CHOICE") {
        let choices = [];
        for (let i = 0; i < answer.choiceAnswer.length; i++) {
          if (answer.choiceAnswer[i] as unknown === true) {
            choices.push(i)
          }
        }
        answer.choiceAnswer = choices;
      }
    }
    let testAnswer: TestAnswer = {
      id: null,
      questionAnswers: this.answers,
      test: this.test,
      user: this.userService.getCurrentUser()
    };
    console.log(testAnswer); // TODO MC Usunąć
    await this.testAnswerService.create(testAnswer);
    await this.router.navigate(['/tests']);
  }

  change() { // TODO MC Usunąć
    console.log(this.answers)
  }
}
