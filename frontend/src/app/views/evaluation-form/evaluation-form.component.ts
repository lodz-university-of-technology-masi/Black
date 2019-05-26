import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TestService} from '../../services/test.service';
import {
  Evaluation,
  Question, QuestionAnswer,
  QuestionAnswerEvaluation,
  Test,
  TestAnswer
} from '../../model/entities';
import {ToastrService} from 'ngx-toastr';
import {EvaluationService} from '../../services/evaluation.service';
import {TestAnswerService} from '../../services/test-answer.service';

@Component({
  selector: 'app-test-form',
  templateUrl: './evaluation-form.component.html',
  styleUrls: ['./evaluation-form.component.css']
})
export class EvaluationFormComponent implements OnInit {
  test: Test;
  testAnswer: TestAnswer;
  evaluation: Evaluation;

  model: {
    question: Question,
    answer: QuestionAnswer,
    evaluation: QuestionAnswerEvaluation
  }[] = [];

  isReadonly = true;

  constructor(private testService: TestService,
              private evaluationService: EvaluationService,
              private testAnswerService: TestAnswerService,
              private router: Router,
              private route: ActivatedRoute,
              private toastr: ToastrService) {
  }

  async ngOnInit() {
    await this.reloadComponent();
  }

  async reloadComponent() {

    const evalId = this.route.snapshot.paramMap.get('id');

    if (evalId === 'new') {
      this.isReadonly = false;
      const ansId = this.route.snapshot.queryParamMap.get('ans');

      this.testAnswer = await this.testAnswerService.getOne(Number.parseInt(ansId));
      this.test = await this.testService.getOne(this.testAnswer.test.id)
      this.model = [];

      const answersEvaluations: QuestionAnswerEvaluation[] = [];

      for (let i = 0; this.testAnswer.questionAnswers.length > i; i++) {
        const questionEvaluations = {
          id: null,
          content: '',
          points: 0,
        };
        answersEvaluations.push(questionEvaluations);

        this.model.push({
          question: this.test.questions[i],
          answer: this.processQuestionAnswer(this.testAnswer.questionAnswers[i]),
          evaluation: questionEvaluations
        });
      }

      this.evaluation = {
        id: null,
        answersEvaluations,
        content: '',
        testAnswer: this.testAnswer,
      };

    } else {
      this.isReadonly = true;
      this.evaluation = await this.evaluationService.getOne(Number.parseInt(evalId));
      this.testAnswer = await this.testAnswerService.getOne(this.evaluation.testAnswer.id);
      this.test = await this.testService.getOne(this.testAnswer.test.id);
      for (let i = 0; this.testAnswer.questionAnswers.length > i; i++) {
        this.model.push({
          question: this.test.questions[i],
          answer: this.processQuestionAnswer(this.testAnswer.questionAnswers[i]),
          evaluation: this.evaluation.answersEvaluations[i]
        });
      }
    }
  }

  private processQuestionAnswer(ans: QuestionAnswer): QuestionAnswer {
    let vals = []
    if (ans.choiceAnswer && ans.choiceAnswer.length) {
      ans.choiceAnswer.forEach(value => {
        vals[value] = true;
      });
      ans.choiceAnswer = vals
    }
    return ans
  }

  async onSave() {
    await this.evaluationService.create(this.evaluation);
    await this.router.navigate(['/answers']);
    this.toastr.info('Ocena została pomyślnie zapisana', 'Sukces', {
      timeOut: 3000,
      closeButton: true,
    });
  }
}
