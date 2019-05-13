import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TestService} from "../../services/test.service";
import {Position, Question, QuestionType, Test} from "../../model/entities";
import {PositionService} from "../../services/position.service";

@Component({
  selector: 'app-test-form',
  templateUrl: './test-form.component.html',
  styleUrls: ['./test-form.component.css']
})
export class TestFormComponent implements OnInit {

  private test: Test;
  private positions: Position[];
  private questionTypes = Object.keys(QuestionType);

  constructor(private testService: TestService, private router: Router, private route: ActivatedRoute, private positionService: PositionService) {
  }

  async ngOnInit() {
    let id = this.route.snapshot.paramMap.get('id');

    if (id === 'new') {
      this.test = {
        id: null,
        name: 'Nowy test',
        group: null,
        language: 'PL',
        position: null,
        questions: [],
      }
    } else {
      this.test = await this.testService.getOne(Number.parseInt(id)).toPromise();
    }

    this.positions = await this.positionService.getAll().toPromise()
  }

  async onSave() {
    if (this.test.id) {
      await this.testService.update(this.test).toPromise();
    } else {
      await this.testService.create(this.test).toPromise();
    }
    await this.router.navigate(['/tests']);
  }

  onAddQuestion() {

    let question: Question = {
      id: null,
      type: QuestionType.OPEN,
      availableChoices: [],
      availableRange: {
        min: 1,
        max: 10,
        step: 0.5
      },
      content: "",
    };

    this.test.questions.push(question)
  }

  trackChoices(index, choice) {
    return index
  }

}
