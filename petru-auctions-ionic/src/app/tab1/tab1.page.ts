import { Component } from '@angular/core';
import { AlertController, ToastController } from '@ionic/angular';
import { environment } from 'src/environments/environment';
import { Globals } from '../globals';
import { RestApiClient } from '../restapiclient';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss']
})
export class Tab1Page {

  version: any;
  apiUrl = environment['apiUrl'];
  auctionHouses = [];

  str = 'pulea';

  constructor(private apiclient: RestApiClient,
    private global: Globals,
    private alertCtrl: AlertController,
    private toastCtrl: ToastController,
    ) {
    apiclient.getVersion()
      .then(data => this.version = data);

    apiclient.getAuctionHouses()
      .then(data => { if (data) this.auctionHouses = data['_embedded']['auctionHouses'] })
  }

  async newActionHouse() {
    if (! this.global.login || this.global.login != 'admin') {
      const alert =  await this.alertCtrl.create({
        header: 'Forbidden',
        message: 'You must be logged in as admin to create a new auction house',
        buttons: [ 'OK' ]
      });
      await alert.present();
    } else {
      const alert = await this.alertCtrl.create({
        header: 'New auction house',
        inputs: [
          {
            type: 'text',
            name: 'auctionHouseName'
          }
        ],
        buttons: [
          { text: 'OK', role: 'OK',
            handler: (data) => this.createNewAuctionHouse(data.auctionHouseName)
          },
          { text: 'Cancel', role: 'Cancel'}
        ]});
        await alert.present();
      }
  }

  async createNewAuctionHouse(name) {
    const result = await this.apiclient.postNewAuctionHouse(name);
    console.log(result);
    this.auctionHouses.push(result);
    await this.presentToast(`Auction house '${result.name}' was created.`);
  }


  async presentToast(msg: string) {
    const toast = await this.toastCtrl.create({
      message: msg,
      duration: 2000
    });
    toast.present();
  }

}
