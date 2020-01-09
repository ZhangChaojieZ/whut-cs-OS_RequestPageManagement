package vo;

public class page {
    private int pageNumber;                     // 页号
    private int blockNumber;                    // 页面号（块号）
    // 以下三个暂未用到
    private int interruptBit;                   // 中断位
    private int ExternalStorageAddress;         // 外存始址
    private int changeBit;                      // 改变位

    public page(){
        this.pageNumber = 0;
        this.blockNumber = -1;
        this.interruptBit = 0;
        this.ExternalStorageAddress = 0;
        this.changeBit = 0;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public int getInterruptBit() {
        return interruptBit;
    }

    public void setInterruptBit(int interruptBit) {
        this.interruptBit = interruptBit;
    }

    public int getExternalStorageAddress() {
        return ExternalStorageAddress;
    }

    public void setExternalStorageAddress(int externalStorageAddress) {
        ExternalStorageAddress = externalStorageAddress;
    }

    public int getChangeBit() {
        return changeBit;
    }

    public void setChangeBit(int changeBit) {
        this.changeBit = changeBit;
    }
}
