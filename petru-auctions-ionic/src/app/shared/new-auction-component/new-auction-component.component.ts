/* eslint-disable @typescript-eslint/naming-convention */
import { Component, Input, OnInit } from '@angular/core';
import { ModalController, ToastController } from '@ionic/angular';
import { Globals } from 'src/app/globals';
import { RestApiClient } from 'src/app/restapiclient';
import { Util } from 'src/app/util';

@Component({
  selector: 'app-new-auction-component',
  templateUrl: './new-auction-component.component.html',
  styleUrls: ['./new-auction-component.component.scss'],
})
export class NewAuctionComponentComponent implements OnInit {

  @Input() backurl: string;
  @Input() auctionHouseId: number;

  name: string;
  description: string;
  startdate: string;
  enddate: string;
  startingprice = 1 + Math.floor(Math.random() * 10);
  nextweek_isostring: string;

  constructor(private modalCtrl: ModalController,
    private apiclient: RestApiClient,
    private toastCtrl: ToastController,
    private global: Globals)
  {
    const today = new Date();
    this.startdate = today.toISOString();
    const nextweek: Date = new Date(today);
    nextweek.setDate(nextweek.getDate() + 7);
    this.nextweek_isostring = nextweek.toISOString();
    this.enddate = this.nextweek_isostring;
  }

  ngOnInit() {}

  async dismiss() {
    await this.modalCtrl.dismiss();
  }

  async postAuction() {
    const obj = {
      name: this.name,
      description: this.description,
      startTime: this.startdate,
      endTime: this.enddate,
      startPrice: this.startingprice,
      auctionHouse : `/api/auctionHouses/${this.auctionHouseId}`,
      seller: `/api/users/${this.global.user.id}`
    };
    // console.log('about to create', obj);
    const newauction = await this.apiclient.postNewAuction(obj);
    // console.log('posted ', newauction);
    Util.presentToast(this.toastCtrl, `Auction ${newauction.id} : "${newauction.name} posted`);
    this.dismiss();
  }

}
