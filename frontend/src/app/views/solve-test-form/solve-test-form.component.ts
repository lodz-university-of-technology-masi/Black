import {Component, OnInit} from '@angular/core';
import {Test} from "../../model/entities";
import {ActivatedRoute} from "@angular/router";
import {TestService} from "../../services/test.service";

@Component({
  selector: 'app-solve-test-form',
  templateUrl: './solve-test-form.component.html',
  styleUrls: ['./solve-test-form.component.css']
})
export class SolveTestFormComponent implements OnInit {

  test: Test;
  private selectedChoices: boolean[][] = [];
  private scaleValue: number[] = [];
  private numberValue: number[] = [];
  private openValue: string[] = [];

  constructor(private testService: TestService,
              private route: ActivatedRoute) {
  }

  async ngOnInit() {
    this.route.url.subscribe(queryParams => {
      this.reloadComponent()
    });
    await this.reloadComponent()
  }

  async reloadComponent() {
    let id = this.route.snapshot.paramMap.get('id');
    this.test = await this.testService.getOne(Number.parseInt(id));
    for (let i = 0; i < this.test.questions.length; i++) {
      if (this.test.questions[i].type === 'CHOICE') {
        this.selectedChoices[i] = [].fill(false);
      }
    }
  }

  change() { // TODO MC Usunąć
    console.log(this.scaleValue)
  }
}
