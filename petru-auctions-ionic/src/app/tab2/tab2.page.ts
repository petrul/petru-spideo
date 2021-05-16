/* eslint-disable @typescript-eslint/dot-notation */
/* eslint-disable radix */
import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AlertController, ModalController, NavController, ToastController } from '@ionic/angular';
import { Globals } from '../globals';
import { RestApiClient } from '../restapiclient';
import { NewAuctionComponentComponent } from '../shared/new-auction-component/new-auction-component.component';
import { Util } from '../util';

@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss']
})
export class Tab2Page {

    auctionHouseId: string;
    auctionHouse: any;
    auctions = [];

    constructor(private apiclient: RestApiClient,
      private route: ActivatedRoute,
      private router: Router,
      private navctrl: NavController,
      private global: Globals,
      private alertCtrl: AlertController,
      private toastCtrl: ToastController,
      private modalCtrl: ModalController) {}

    ngOnInit() {
      this.auctionHouseId = this.route.snapshot.paramMap.get('id');
      this.retrieveData();
    }

    retrieveData() {
      if (this.auctionHouseId) {
        this.apiclient.getAuctionHouse(parseInt(this.auctionHouseId))
          .then(data => this.auctionHouse = data);
        this.apiclient.getAuctionsFromFacade(parseInt(this.auctionHouseId))
          .then(data => { this.auctions = data; });
        }
    }

    gotoBiddings(auctionId: number) {
      this.navctrl.navigateForward(`/tabs/tab3/${auctionId}`);
    }

    async newAuction() {
      if (!this.global.login) {
        Util.alert(this.alertCtrl, 'You must be logged in.');
        return;
      }
      const modal = await this.modalCtrl.create({
        component: NewAuctionComponentComponent,
        componentProps: {
          backurl : this.router.url,
          auctionHouseId: this.auctionHouseId
        },
        swipeToClose: true,
        showBackdrop: true,
        animated: true,
      });
      modal.onDidDismiss().then( newauction => {
        this.retrieveData();
      });
      await modal.present();
    }
}
