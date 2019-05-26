import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {TestService} from "../../services/test.service";
import {Test} from "../../model/entities";
import {Router} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";
import {UserService} from "../../services/user.service";
import {UtilsService} from "../../services/utils.service";
import {FileUploader} from "ng2-file-upload";

@Component({
  selector: 'app-tests',
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.css']
})
export class TestsComponent implements OnInit {
  tests: Test[];

  @ViewChild('fileInput') fileInput: ElementRef;

  uploader: FileUploader;
  isDropOver: boolean;

  constructor(private sanitizer: DomSanitizer,
              private testService: TestService,
              private router: Router,
              public userService: UserService) {
  }

  ngOnInit(): void {
    this.loadTests();
    const headers = [{name: 'Accept', value: '*/*'}];
    this.uploader = new FileUploader({url: 'api/' + UtilsService.FILES_URL + '/import', autoUpload: true, headers: headers});
    this.uploader.onCompleteAll = () => {
      this.loadTests();
      alert('Plik załadowany')};
  }

  async loadTests() {

    this.tests = await this.testService.getAll();

  }

  async onDeleteTest(test: Test) {
    await this.testService.delete(test);
    await this.loadTests();
  }

  onEditTest(test: Test) {
    this.router.navigate(['/tests', test.id]);
  }

  onAuthorize(test: Test) {
    this.router.navigate(['/permissions', test.id]);
  }

  onSolveTest(test: Test) {
    this.router.navigate(['/solve', test.id]);
  }

  onExportTest(test: Test) {
    return "api/" + UtilsService.FILES_URL + "/" + test.id
  }

  onCreateTest() {
    this.router.navigate(['/tests', 'new']);
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  fileClicked() {
    this.fileInput.nativeElement.click();
  }
}
