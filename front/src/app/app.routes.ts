import { Routes } from '@angular/router';
import {ListPage as TeamsListPage} from './teams/list-page/list-page';
import {ViewPage as TeamsViewPage} from './teams/view-page/view-page';
import {CreatePage as TeamsCreatePage} from './teams/create-page/create-page';

export const routes: Routes = [
  { path: "", component: TeamsListPage },
  { path: "new", component: TeamsCreatePage },
  { path: "teams/:id", component: TeamsViewPage}
];
