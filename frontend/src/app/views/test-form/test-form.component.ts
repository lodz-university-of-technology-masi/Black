import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TestService} from "../../services/test.service";
import {Position, Question, QuestionType, Test} from "../../model/entities";
import {PositionService} from "../../services/position.service";
import {ContextMenuComponent, ContextMenuService} from "ngx-contextmenu";
import {UtilsService} from "../../services/utils.service";

@Component({
  selector: 'app-test-form',
  templateUrl: './test-form.component.html',
  styleUrls: ['./test-form.component.css']
})
export class TestFormComponent implements OnInit {

  private test: Test;
  private positions: Position[];
  private questionTypes = Object.keys(QuestionType);

  private selectedText: string;
  private foundSynonyms: string[] = [];


  @ViewChild(ContextMenuComponent) public basicMenu: ContextMenuComponent;

  constructor(private testService: TestService,
              private router: Router,
              private route: ActivatedRoute,
              private positionService: PositionService,
              private utilsService: UtilsService ) {
  }

  async ngOnInit() {
    this.route.url.subscribe(queryParams => {
      this.reloadComponent()
    });
    await this.reloadComponent()
  }

  async reloadComponent(){
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
      this.test = await this.testService.getOne(Number.parseInt(id));
    }

    this.positions = await this.positionService.getAll();
  }

  async onSave() {
    if (this.test.id) {
      await this.testService.update(this.test);
    } else {
      await this.testService.create(this.test);
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

  async onTranslate(){
    let translatedTest = await this.testService.translateTest(this.test.id, "EN")
    await this.router.navigate(['/tests', translatedTest.id]);
  }

  trackChoices(index, choice) {
    return index
  }

  onSelectionChange(evt) {
    const start = evt.target.selectionStart;
    const end = evt.target.selectionEnd;

    this.selectedText = evt.target.value.substr(start, end - start);
  }

  onContextMenuClosed(){
    this.selectedText = null;
    this.foundSynonyms = null;
  }

  async searchSynonyms(){
    this.foundSynonyms = await this.utilsService.findSynonyms(this.selectedText);
    this.selectedText = null;
  }

  searchWiki() {
    // TODO obsługa wikipedii
    console.log('search wikipedia: ', this.selectedText)
  }

  public showContextMenuItems = (item: any): boolean => {
    return !this.foundSynonyms;
  }

}
