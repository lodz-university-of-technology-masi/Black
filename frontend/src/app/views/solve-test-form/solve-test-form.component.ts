import {Component, HostListener, OnInit} from '@angular/core';
import {QuestionAnswer, Test, TestAnswer} from '../../model/entities';
import {ActivatedRoute, Router} from '@angular/router';
import {TestService} from '../../services/test.service';
import {TestAnswerService} from '../../services/test-answer.service';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';

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
              private route: ActivatedRoute,
              private toastr: ToastrService) {
  }

  async ngOnInit() {
    await this.reloadComponent();
  }

  async reloadComponent() {
    const id = this.route.snapshot.paramMap.get('id');
    this.answers = [];
    this.test = await this.testService.getOne(Number.parseInt(id));

    for (const question of this.test.questions) {
      const answer: QuestionAnswer = {
        id: null,
        type: question.type,
        choiceAnswer: []
      };
      this.answers.push(answer);
    }
  }

  async onSave() {
    for (const answer of this.answers) {
      if (answer.type === 'CHOICE') {
        const choices = [];
        for (let i = 0; i < answer.choiceAnswer.length; i++) {
          if (answer.choiceAnswer[i] as unknown === true) {
            choices.push(i);
          }
        }
        answer.choiceAnswer = choices;
      }
    }
    const testAnswer: TestAnswer = {
      id: null,
      questionAnswers: this.answers,
      test: this.test,
      user: this.userService.getCurrentUser()
    };
    await this.testAnswerService.create(testAnswer);
    await this.router.navigate(['/tests']);
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
