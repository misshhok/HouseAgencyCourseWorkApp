import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cities',
        data: { pageTitle: 'agencyCourseWorkApp.cities.home.title' },
        loadChildren: () => import('./cities/cities.module').then(m => m.CitiesModule),
      },
      {
        path: 'streets-of-cities',
        data: { pageTitle: 'agencyCourseWorkApp.streetsOfCities.home.title' },
        loadChildren: () => import('./streets-of-cities/streets-of-cities.module').then(m => m.StreetsOfCitiesModule),
      },
      {
        path: 'addresses',
        data: { pageTitle: 'agencyCourseWorkApp.addresses.home.title' },
        loadChildren: () => import('./addresses/addresses.module').then(m => m.AddressesModule),
      },
      {
        path: 'types-of-residental-estate',
        data: { pageTitle: 'agencyCourseWorkApp.typesOfResidentalEstate.home.title' },
        loadChildren: () =>
          import('./types-of-residental-estate/types-of-residental-estate.module').then(m => m.TypesOfResidentalEstateModule),
      },
      {
        path: 'statuses-of-residental-estate',
        data: { pageTitle: 'agencyCourseWorkApp.statusesOfResidentalEstate.home.title' },
        loadChildren: () =>
          import('./statuses-of-residental-estate/statuses-of-residental-estate.module').then(m => m.StatusesOfResidentalEstateModule),
      },
      {
        path: 'purposes-of-non-residental-estate',
        data: { pageTitle: 'agencyCourseWorkApp.purposesOfNonResidentalEstate.home.title' },
        loadChildren: () =>
          import('./purposes-of-non-residental-estate/purposes-of-non-residental-estate.module').then(
            m => m.PurposesOfNonResidentalEstateModule
          ),
      },
      {
        path: 'building-type-of-non-residental-estate',
        data: { pageTitle: 'agencyCourseWorkApp.buildingTypeOfNonResidentalEstate.home.title' },
        loadChildren: () =>
          import('./building-type-of-non-residental-estate/building-type-of-non-residental-estate.module').then(
            m => m.BuildingTypeOfNonResidentalEstateModule
          ),
      },
      {
        path: 'types-of-commercial-events',
        data: { pageTitle: 'agencyCourseWorkApp.typesOfCommercialEvents.home.title' },
        loadChildren: () =>
          import('./types-of-commercial-events/types-of-commercial-events.module').then(m => m.TypesOfCommercialEventsModule),
      },
      {
        path: 'clients',
        data: { pageTitle: 'agencyCourseWorkApp.clients.home.title' },
        loadChildren: () => import('./clients/clients.module').then(m => m.ClientsModule),
      },
      {
        path: 'residental-estates',
        data: { pageTitle: 'agencyCourseWorkApp.residentalEstates.home.title' },
        loadChildren: () => import('./residental-estates/residental-estates.module').then(m => m.ResidentalEstatesModule),
      },
      {
        path: 'non-residental-estates',
        data: { pageTitle: 'agencyCourseWorkApp.nonResidentalEstates.home.title' },
        loadChildren: () => import('./non-residental-estates/non-residental-estates.module').then(m => m.NonResidentalEstatesModule),
      },
      {
        path: 'comercial-events-of-non-residental-estate',
        data: { pageTitle: 'agencyCourseWorkApp.comercialEventsOfNonResidentalEstate.home.title' },
        loadChildren: () =>
          import('./comercial-events-of-non-residental-estate/comercial-events-of-non-residental-estate.module').then(
            m => m.ComercialEventsOfNonResidentalEstateModule
          ),
      },
      {
        path: 'comercial-events-of-residental-estate',
        data: { pageTitle: 'agencyCourseWorkApp.comercialEventsOfResidentalEstate.home.title' },
        loadChildren: () =>
          import('./comercial-events-of-residental-estate/comercial-events-of-residental-estate.module').then(
            m => m.ComercialEventsOfResidentalEstateModule
          ),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
