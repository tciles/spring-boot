import { Routes } from '@angular/router';
import {ListPage as TeamsListPage} from './teams/list-page/list-page';
import {ViewPage as TeamsViewPage} from './teams/view-page/view-page';
import {CreatePage as TeamsCreatePage} from './teams/create-page/create-page';
import {AddPlayerPage} from './teams/add-player-page/add-player-page';

export const routes: Routes = [
  { path: "", component: TeamsListPage },
  { path: "new", component: TeamsCreatePage },
  { path: "teams/:id", component: TeamsViewPage},
  { path: "teams/:id/add-player", component: AddPlayerPage}
];
