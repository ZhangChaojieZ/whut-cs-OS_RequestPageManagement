import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dataUtil.pageManagement;
import vo.*;

public class main {
    public static void main(String[] args) {
        int inputPageNum;           // 输入的内存页面数
        int inputPageSize;          // 输入的页面大小
        int inputProcessQuantity;   // 输入的进程的个数
        int inputProcessNum = 0;    // 输入的进程号
        int inputProcessPageSize;   // 输入进程的页表长度
        int inputMemoryBlockNum;    // 输入分配的物理块数
        int inputMenu;              // 菜单编号
        int requestPage;            // 输入请求的页号

        List<requestProcess> requestTable = new ArrayList<>();      // 请求表
        List<Integer> memory = new ArrayList<>();                   // 存储页面表

        Scanner input = new Scanner((System.in));
        System.out.println("请输入内存页大小(单位：KB)：");
        inputPageSize = input.nextInt();
        System.out.println("请输入内存页面数：");
        inputPageNum = input.nextInt();
        System.out.println("请输入进程的个数：");
        inputProcessQuantity = input.nextInt();

        // 初始化存储页面表
        for(int i = 0;i < inputPageNum;i++) {
            memory.add(0);
        }
        // -------

        // 添加进程到请求表
        requestProcess req;
        int i = 0;
        while(i < inputProcessQuantity){
            System.out.println("请输入进程" + i + "的页表长度：");
            inputProcessPageSize = input.nextInt();
            req = new requestProcess();
            req.setProcessNum(inputProcessNum);
            inputProcessNum++;
            //req.setPageLength(inputProcessPageSize);
            req.setPageQuantity(inputProcessPageSize);
            // 初始化进程的页表
            List<page> tempList = new ArrayList<>();
            for(int j = 0;j < inputProcessPageSize;j++) {
                page tempPage = new page();
                tempPage.setPageNumber(j);
                tempList.add(tempPage);
            }
            req.setPageTable(tempList);
            // ---------
            requestTable.add(req);
            i++;
        }
        // -----------

        while(true) {
            System.out.println("******菜单******");
            System.out.println("\t1.分配物理块\n\t2.回收物理块\n\t3.分配页\n\t4.查看进程页表\n\t5.查看内存中进程的情况\n\t" +
                    "6.查看内存使用情况\n\t" + "7.退出");
            System.out.println("请输入菜单编号：");
            inputMenu = input.nextInt();
            if(inputMenu == 1){
                pageManagement.displayRequestProcess(requestTable);
                System.out.println("请输入要进行分配进程号：");
                inputProcessNum = input.nextInt();

                // 判断进程是否已经进行分配过物理块，如果已分配，则不再进行分配；否则进行分配
                if(requestTable.get(inputProcessNum).getPageStatue().equals("已分配")) {
                    System.out.println("该进程以进行分配过物理块，不再进行分配！");
                    continue;
                }

                System.out.println("请输入分配给进程的物理块数：");
                inputMemoryBlockNum = input.nextInt();
                if(pageManagement.addProcesstoMemory(inputMemoryBlockNum,requestTable.get(inputProcessNum),memory)) {
                    System.out.println("分配成功！");
                }else {
                    System.out.println("分配失败！");
                }

            }else if(inputMenu == 2) {
                pageManagement.displayProcessInmem(requestTable);       // 展示内存中的进程
                System.out.println("请输入要进行分配进程号：");
                inputProcessNum = input.nextInt();
                pageManagement.deleteProcessFromMemory(requestTable.get(inputProcessNum),memory);
                System.out.println();

            }else if(inputMenu == 3) {
                pageManagement.displayProcessInmem(requestTable);                       // 展示内存中的进程
                System.out.println("请输入要进行分配进程号：");
                inputProcessNum = input.nextInt();
                pageManagement.displayPage(requestTable.get(inputProcessNum));          //  展示页表情况
                /*System.out.println("请输入请求的页：");
                requestPage = input.nextInt();

                // 输出内存中已有的页的页号
                System.out.println("内存中已有的页的页号：");
                for(int s:requestTable.get(inputProcessNum).getListLRU()) {
                    System.out.print(s + " ");
                }
                System.out.println();
                // -------------------
*/
                /*if(pageManagement.requestPageLRU(requestPage,requestTable.get(inputProcessNum))) {
                    System.out.println("分配成功！");
                }else {
                    System.out.println("分配失败！");
                }
*/

                List<Integer> listVisit = new ArrayList<>();                            // 访问序列
                List<Integer> listMissPage = new ArrayList<>();

                System.out.println("请输入访问序列：");
                // 输入序列
                String tempLine = input.nextLine();
                String line = input.nextLine();
                Scanner in = new Scanner(line);
                while(in.hasNext()) {
                    listVisit.add(in.nextInt());
                }
                // 判断该序列中是否存在不合理数据
                boolean errorFlag = false;  // 数据不合理，则为true
                for(int s:listVisit) {
                    if(s > requestTable.get(inputProcessNum).getPageQuantity()) {
                        System.out.println(s + "页不存在，请求错误！");
                        errorFlag = true;
                    }
                }
                if(!errorFlag) {
                    for(int k = 0;k < listVisit.size();k++) {
                        if(!pageManagement.requestPageLRU(listVisit.get(k),requestTable.get(inputProcessNum),listMissPage)) {
                            //listMissPage.add(listVisit.get(k));
                        }
                    }
                    System.out.println("依次淘汰的页号：");
                    for(int s:listMissPage) {
                        System.out.print(s + " ");
                    }
                    System.out.println();
                }


            }else if(inputMenu == 4) {
                pageManagement.displayRequestProcess(requestTable);
                System.out.println("请输入要查看的进程的进程号：");
                inputProcessNum = input.nextInt();
                pageManagement.displayPage(requestTable.get(inputProcessNum));

            }else if(inputMenu == 5) {
                pageManagement.displayProcessInmem(requestTable);   // 内存中的进程
            }else if(inputMenu == 6) {
                pageManagement.displayMemory(memory);
            }else if(inputMenu == 7) {
                break;
            }else{
                System.out.println("输入错误,请重新输入");
                continue;
            }
        }

    }
}
