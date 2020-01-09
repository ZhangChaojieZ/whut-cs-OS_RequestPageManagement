package vo;

import java.util.ArrayList;
import java.util.List;

public class requestProcess {
    private int processNum;             // 进程号
    private int pageQuantity;           // 页表长度
    //private int pageAddress;          // 页表始址
    //private int pageLength;           // 页表长度
    private String pageStatue;          // 进程分配内存的状态
    private int memoryBlockNum;         // 分配给进程的物理块数量
    private List<page> pageTable;       // 页表
    private List<Integer> listLRU;     // 大小为分配的物理块数的访问序列

    public requestProcess(){
        this.processNum = 0;
        this.pageQuantity = 0;
        this.pageStatue = "未分配";
        this.memoryBlockNum = 0;
        this.listLRU = new ArrayList<>();
        this.pageTable = new ArrayList<>();
    }

    public int getProcessNum() {
        return processNum;
    }

    public void setProcessNum(int processNum) {
        this.processNum = processNum;
    }

    public int getPageQuantity() {
        return pageQuantity;
    }

    public void setPageQuantity(int pageQuantity) {
        this.pageQuantity = pageQuantity;
    }

/*    public int getPageAddress() {
        return pageAddress;
    }

    public void setPageAddress(int pageAddress) {
        this.pageAddress = pageAddress;
    }*/

   /* public int getPageLength() {
        return pageLength;
    }

    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }*/

    public String getPageStatue() {
        return pageStatue;
    }

    public void setPageStatue(String pageStatue) {
        this.pageStatue = pageStatue;
    }

    public int getMemoryBlockNum() {
        return memoryBlockNum;
    }

    public void setMemoryBlockNum(int memoryBlockNum) {
        this.memoryBlockNum = memoryBlockNum;
    }

    public List<page> getPageTable() {
        return pageTable;
    }

    public void setPageTable(List<page> pageTable) {
        this.pageTable = pageTable;
    }

    public List<Integer> getListLRU() {
        return listLRU;
    }

    public void setListLRU(List<Integer> listLRU) {
        this.listLRU = listLRU;
    }
}
