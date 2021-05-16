import { BiddingDto } from "./BiddingDto";

export class AuctionDto {
  id:   number;
  name: string;
  description: string;
  startTime: Date;
  endTime:  Date;
  startPrice: number;
  seller: any;
  biddings: Array<BiddingDto>;

  isClosed(): boolean {
    return this.endTime.getTime() < Date.now();
  }

  get crtPrice(): number {
    let max = -1;

    for (const it of this.biddings) {
      if (it.price > max) {
        max = it.price;
      }
    }
    if (max < 0) {
      max = this.startPrice;
    }

    return max;
  }
}
