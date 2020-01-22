import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {ColdOpenInfoService} from '../../../@core/data/cold-open-info.service';

@Component({
    selector: 'ngx-clod-project',
    template: `
        <div class="tools-btn-group">
                <button type="button" class="btn btn-success with-margins"
                        (click)="selectAll()">全选</button>
                <button type="button" class="btn btn-success with-margins"
                        (click)="selectContrary()">反选</button>
                <button type="button" class="btn btn-success with-margins"
                        (click)="selectCancel()">不选</button>
        </div>
        <br>
        <div class="row">
            <div class="row col-md-12">
                <div *ngFor="let project of projects">
                    <ngx-status-card [title]="project.idAndName"
                                     type="success"
                                     [on]="project.disable"
                                     [id]="project.id"
                                     [ids]="selectId"
                                     (valueChange)="changeProjectId($event)">
                        <i ngClass="icon ion-md-checkmark"></i>
                    </ngx-status-card>
                </div>
            </div>
        </div>
    `,
})
export class ProjectConfigComponent implements OnInit, OnDestroy {
    // @Input() projects: any;
    @Input() selectId: any;

    @Output() valueChange = new EventEmitter<any>();

    projects: any[];

    projectList: any[];


    constructor(private coldOpenInfoService: ColdOpenInfoService) { }

    ngOnInit() {
        this.setData();
    }

    setData() {
        // TODO 根据角色控制可配置项目
        this.coldOpenInfoService.getProjectList().subscribe(response => {
            this.projectList = response.body;
            this.initProjects();
        });
    }

    initProjects() {
        this.projects = [];
        this.projectList.forEach(value => {
            const project = {id: 0, idAndName: '', disable: false};
            project.id = value.id;
            project.idAndName = value.id + '_' + value.appName;
            if (this.selectId && this.selectId.includes(project.id)) {
                project.disable = true;
            }
            this.projects.push(project);
        });
    }

    ngOnDestroy() {
        this.selectId = [];
    }

    changeProjectId(ids) {
        this.valueChange.emit(ids);
    }

    selectContrary() {
        this.projects.forEach(value => {
            if (this.selectId.includes(value.id)) {
                value.disable = null;
                const index = this.selectId.findIndex( v => v === value.id);
                this.selectId.splice(index, 1);
            } else {
                value.disable = true;
                this.selectId.push(value.id);
            }
        });
        this.changeProjectId(this.selectId);
    }

    selectAll() {
        this.selectId = [];
        this.projects.forEach(value => {
            value.disable = true;
            this.selectId.push(value.id);
        });
        this.changeProjectId(this.selectId);
    }

    selectCancel() {
        this.selectId = [];
        this.projects.forEach(value => {
            value.disable = false;
        });
        this.changeProjectId(this.selectId);
    }


}
