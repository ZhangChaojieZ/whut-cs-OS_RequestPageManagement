package dataUtil;

import vo.page;
import vo.requestProcess;

import java.util.ArrayList;
import java.util.List;

public class pageManagement {
    // 展示内存的使用情况
    public static void displayMemory(List<Integer> mem) {
        System.out.println("内存使用情况如下（存储页面表位视图法）：");
        for(int i = 0;i < mem.size();i++) {
            if((i + 1) % 64 == 0) {
                System.out.println(mem.get(i));
            }else {
                System.out.print(mem.get(i));
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    // 展示请求表的情况
    public static void displayRequestProcess(List<requestProcess> req) {
        System.out.println("-进程号-\t-页表长度-\t-已分配物理块数-\t状态");
        for(int i = 0;i < req.size();i++) {
            System.out.println(req.get(i).getProcessNum() + "\t\t\t" + req.get(i).getPageQuantity() + "\t\t\t" +
                    req.get(i).getMemoryBlockNum() + "\t\t\t" + req.get(i).getPageStatue());
        }

    }

    // 展示内存中进程的情况
    public static void displayProcessInmem(List<requestProcess> req) {
        System.out.println("-进程号-\t-页表长度-\t-已分配物理块数-\t状态");
        for(int i = 0;i < req.size();i++) {
            if(req.get(i).getPageStatue().equals("已分配")) {
                System.out.println(req.get(i).getProcessNum() + "\t\t\t" + req.get(i).getPageQuantity() + "\t\t\t" +
                        req.get(i).getMemoryBlockNum() + "\t\t\t" + req.get(i).getPageStatue());
            }
        }

    }

    // 展示某个进程的页表
    public static void displayPage(requestProcess requestPro) {
        /*System.out.println("-页号-\t-页面号-\t-中断位-");
        List<page> pagetable = requestPro.getPageTable();
        for(int i = 0;i < pagetable.size();i++) {
            System.out.println(pagetable.get(i).getPageNumber() + "\t\t" + pagetable.get(i).getBlockNumber() + "\t\t"
                    + pagetable.get(i).getInterruptBit());
        }*/

        System.out.println("-页号-\t-页面号-");
        List<page> pagetable = requestPro.getPageTable();
        for(int i = 0;i < pagetable.size();i++) {
            System.out.println(pagetable.get(i).getPageNumber() + "\t\t" + pagetable.get(i).getBlockNumber());
        }
    }

    // 分配内存物理块，更新存储页面表，并且更新进程的页表的页面号。true表示成功分配，false表示失败
    // blockNum为分配给进程的物理块的数量，requestPro为要分配的进程，mem为存储页面表
    public static boolean addProcesstoMemory(int blockNum,requestProcess requestPro,List<Integer> mem) {
        int i,j;
        int count = 0;  // 判断是否有足够的内存空间进行分配

        // 扫描存储分配表，查看是否有足够的内存空间进行分配
        for(int k = 0;k < mem.size();k++) {
            if(mem.get(k) == 0) {
                count++;
            }
        }
        if(count < blockNum){
            System.out.println("内存不足，无法分配！");
            return false;
        }
        // ----------------------------------------

        // 进行物理块（页面）分配
        for(i = 0;i < blockNum;i++) {
            for(j = 0;j < mem.size();j++) {
                if(mem.get(j) == 0) {
                    requestPro.getPageTable().get(i).setBlockNumber(j);     // 将进程的页表中的页面号设置为对应的内存中的物理块号
                    requestPro.setPageStatue("已分配");                      // 修改进程分配内存的状态
                    mem.set(j,1);                                           // 更改存储页面表相应位置为1
                    break;
                }
            }
            requestPro.getListLRU().add(i);                                 // 初始化访问序列为从0到物理块数量的页号
        }
        requestPro.setMemoryBlockNum(blockNum);                             // 设定进程的物理块数量
        return true;
    }

    // 回收内存块，更改进程的分配状态，更改存储页面表的比特位
    public static boolean deleteProcessFromMemory(requestProcess requestPro,List<Integer> mem) {
        List<page> listPage = requestPro.getPageTable();
        for(int i = 0;i < requestPro.getPageTable().size();i++) {
            requestPro.getPageTable().get(i).setBlockNumber(-1);                 // 将进程的页表中的页面号设置为初始值-1
            // 将已分配过的内存回收，即存储页面表比特位置零
            if(requestPro.getPageTable().get(i).getBlockNumber() != -1) {
                mem.set(requestPro.getPageTable().get(i).getBlockNumber(), 0);        // 更改存储页面表相应位置为0
            }
        }
        requestPro.setPageStatue("未分配");
        return true;
    }

    // 请求分页,返回true表示没有缺页；返回false表示进行缺页中断
    // requestPro为要进行相关操作的进程
    public static boolean requestPageLRU(int pageNum, requestProcess requestPro,List<Integer> listMissPage) {
        List<page> listPage = requestPro.getPageTable();
        List<Integer> listLRU = requestPro.getListLRU();

        for(int i = 0;i < listLRU.size();i++) {
            if(pageNum == listLRU.get(i)) {
                System.out.println("请求页" + pageNum + "在内存中，无需进行调入！");

                // 修改LRU序列，移动该页到序列尾部，即相当于实现了最近最久未使用算法
                for(int j = i;j < listLRU.size() - 1;j++){
                    listLRU.set(j,listLRU.get(j+1));
                }
                listLRU.set(listLRU.size() - 1,pageNum);
                return true;
            }
        }

        /*
        // 如果需要淘汰，则淘汰访问序列中最左边的页号
        // 修改页表，将被淘汰页的页面号赋给新加入序列的页，并将被淘汰的页在页表中对应的页面号还原为初始值-1
        page tempPage = new page();
        tempPage.setBlockNumber(listPage.get(listVisit.get(0)).getBlockNumber());
        listPage.set(pageNum,tempPage);
        tempPage.setBlockNumber(-1);
        listPage.set(listVisit.get(0),tempPage);
        requestPro.setPageTable(listPage);
        */

        // 修改页表，将被淘汰页的页面号赋给新加入序列的页，并将被淘汰的页在页表中对应的页面号还原为初始值-1
        System.out.println("请求页" + pageNum + "不在内存中，进行缺页中断，淘汰" + listLRU.get(0));
        listMissPage.add(listLRU.get(0));
        listPage.get(pageNum).setBlockNumber(listPage.get(listLRU.get(0)).getBlockNumber());    // 更改调入的页的页面号
        listPage.get(listLRU.get(0)).setBlockNumber(-1);                                        // 更改被淘汰页的页面号
        requestPro.setPageTable(listPage);
        // --------------------------------

        // 修改LRU序列，实现LRU算法，每次淘汰LRU序列最左边的页
        for(int i = 0;i < listLRU.size() - 1;i++) {
            listLRU.set(i,listLRU.get(i+1));
        }
        listLRU.set(listLRU.size() - 1,pageNum);
        requestPro.setListLRU(listLRU);
        // -------------

        return false;
    }






}
