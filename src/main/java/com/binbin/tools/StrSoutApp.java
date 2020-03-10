package com.binbin.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StrSoutApp {

    public static void main(String[] args) {
//        String sDate = "2019/12/04";
        String sDate = "2020/01/05";
        String sCode = "ZX12CW";
        String trustProjectCode = "ZX12CW";
        int gracedays = 0;
        int days = 0;
//        business_status_temp(sDate, sCode);
//        bussiness_balance_temp(sDate, sCode);
//        insert_business_over(sDate, gracedays);
//        insert_over_status(sDate, sCode, days);

//        String btSqlQuery = "select bt.CONTRACTNO as CONTRACTNO, bt.TRUSTPROJECTCODE as TRUSTPROJECTCODE, bt.APPLYNO as APPLYNO, bt.BEGINDATE as BEGINDATE, bt.DEADLINE as DEADLINE, bt.CONTRACTSUM as CONTRACTSUM, bt.WITHHOLDDATE as WITHHOLDDATE, bt.ENDDATE as ENDDATE, bc.BALANCE as BALANCE "
//                + " from batch_temporary bt,business_contract bc "
//                + " where bt.contractno=bc.contractno and bt.trustprojectcode='" + trustProjectCode + "' and bc.begindate>='2019/12/13' " // 测试使用 and bt.filedate='2019/12/13'
//                + " and not exists(select td.ai_apply_id from TONGDUN_FEEDBACK td where td.AI_LOAN_ID=bt.contractno and td.loan_status='settle' and td.success='true')";
//        System.out.println(btSqlQuery);
//        StringBuffer sql = new StringBuffer(
//                "select file_time,file_size from FILE_HANDLE where trust_project_code='");
//        sql.append(trustProjectCode).append("' and file_name='").append("'")
//                .append(" and verify_result='").append(1).append("'")
//                .append(" and SIGN_RESULT not in('").append(0).append("','").append(9).append("')");
//        System.out.println(sql.toString());

        System.out.println(amsSql());
    }

    private static String amsSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT l.loanIssue,l.repaymentType,l.interestRateType,l.interestRate,l.loanuse,l.applicationArea,l.applyAmount,l.loanAmount,l.loanAmount*l.loanIssue*l.interestRate/1200 as interest,substring(l.loanTime, 1, 10) as loanDate,");
        sql.append("(case l.loanuse when '01' then 'K70' when '02' then 'C36' when '03' then 'R90' when '04' then 'L72' else 'H62' end) as loanto,");
        sql.append("c.sex,c.eduexperience,c.marriage,c.monthlyIncome ");
        sql.append("FROM async_risk_user_info c, async_risk_loan_info l, async_risk_control_info r ");
        sql.append("WHERE c.controlId = r.id AND c.controlId = l.controlId ");
        sql.append("and r.out_trade_no = ?");
        return sql.toString();
    }

    private static void insert_over_status(String sDate, String sCode, int days) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from over_status where trim(trustprojectcode)= '");
        sql.append(sCode).append("' and filedate = '").append(sDate).append("'");
        System.out.println("deleteSql=" + sql.toString());
        sql = new StringBuffer();
        sql.append("insert into over_status ");
        sql.append(" select '").append(sCode).append("','").append(sDate).append("',");//项目简码,业务日期
        sql.append("sum(contractsum),count(*),");//放款规模、放款笔数
        sql.append("sum(case when balance1>0 then 1 else 0 end),");//在贷笔数
        sql.append("sum(balance2)");//真实在贷余额
        sql.append(",sum(balance1),");//在贷余额
        sql.append("sum(case when overdays1 >= ").append(days).append(" then contractsum else 0 end),");//逾期放款规模
        sql.append("sum(case when nvl(overdays1,-1) < ").append(days).append(" then 0 else 1 end),");//逾期笔数
        sql.append("sum(case when overdays2 >=").append(days).append(" then balance2 else 0 end ),");//真实逾期余额
        sql.append("sum(case when overdays1 >=").append(days).append(" then balance1 else 0 end ),");//逾期余额
        sql.append("sum(case when overdays1 between ").append(days).append(" and 7 then balance1 else 0 end),");//逾期7天以下余额
        sql.append("sum(case when overdays1 between 8 and 15 then balance1 else 0 end),");//逾期8天到15天余额
        sql.append("sum(case when overdays1 between 16 and 30 then balance1 else 0 end),");//逾期16天到30天余额
        sql.append("sum(case when overdays1 between 31 and 60 then balance1 else 0 end),");//逾期31天到60天余额
        sql.append("sum(case when overdays1 between 61 and 90 then balance1 else 0 end),");//逾期61天到90天余额
        sql.append("sum(case when overdays1 > 90 then balance1 else 0 end),");//逾期91天+余额
        sql.append("'").append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS").format(new Date()));//插入日期
        sql.append("',null,null,null,null,null,null,null,null");
        sql.append(" from business_balance_temp");
        System.out.println(sql.toString());
    }

    private static void insert_business_over(String sDate, int gracedays) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into business_over select TRUSTPROJECTCODE,'").append(sDate).append("',CONTRACTNO,CONTRACTSUM,PAYPRINCIPALAMT,ACTUALCORP1,OVERSEQID1,OVERDATE1,OVERDAYS1 ");
        sql.append(" from business_balance_temp t where t.status1='1' and t.overdays1 >=").append(gracedays);
        System.out.println(sql.toString());
    }

    private static void business_status_temp(String sDate, String sCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table business_status_temp tablespace bhxt_temp as  select ");
        sql.append("nvl(a.trustprojectcode,b.trustprojectcode) as trustprojectcode,");
        sql.append("nvl(a.objectno,b.objectno) as objectno,");
        sql.append("nvl(a.payplannum,0) as payplannum,");
        sql.append("a.maxpaydate as maxpaydate,");
        sql.append("nvl(a.payprincipalamt,0) as payprincipalamt,");
        sql.append("b.maxoccurdate as maxoccurdate,");
        sql.append("nvl(b.actualcorp1,0) as actualcorp1,");
        sql.append("nvl(b.actualcorp2,0) as actualcorp2,");
        sql.append("nvl(b.returnnums1,0) as returnnums1,");
        sql.append("nvl(b.returnnums2,0) as returnnums2,");
        sql.append("case when nvl(a.payplannum,0)>nvl(b.returnnums1,0) and nvl(a.payprincipalamt,0)> nvl(b.actualcorp1,0) then 1 else 0 end as overstatus1,");
        sql.append("case when nvl(a.payplannum,0)>nvl(b.returnnums2,0) and nvl(a.payprincipalamt,0)> nvl(b.actualcorp2,0) then 1 else 0 end as overstatus2");
        sql.append(" from ");
        sql.append("(select pst.trustprojectcode,pst.objectno,");
        sql.append("count(*) as payplannum,");
        sql.append("max(pst.paydate) as maxpaydate,");
        sql.append("sum(pst.payprincipalamt) as payprincipalamt");
        sql.append(" from payment_schedule pst");
        sql.append(" where pst.trustprojectcode='").append(sCode).append("' and pst.paydate<='").append(sDate).append("'");
        sql.append(" group by pst.trustprojectcode,pst.objectno) a");
        sql.append(" full join");
        sql.append(" (select pdt.trustprojectcode,pdt.objectno,");
        sql.append("max(pdt.occurdate) as maxoccurdate,");
        sql.append("sum(case when pdt.paytype not in ('12', '13', '17', '31', '32') then pdt.actualcorp+nvl(pdt.crosspayserviceprincipal,0) else 0 end) as actualcorp1,");
        sql.append("sum(case when pdt.paytype not in('10','16','19','20') then pdt.actualcorp+nvl(pdt.crosspayserviceprincipal,0) else 0 end) as actualcorp2,");
        sql.append("sum(case when pdt.paytype not in ('12', '13', '17', '31', '32') then 1 else 0 end) as returnnums1,");
        sql.append("sum(case when pdt.paytype not in ('10','16','19','20') then 1 else 0 end) as returnnums2");
        sql.append(" from payment_detail pdt");
        sql.append(" where pdt.trustprojectcode='").append(sCode).append("' and pdt.occurdate<='").append(sDate).append("'");
        sql.append(" group by pdt.trustprojectcode,pdt.objectno) b");
        sql.append(" on a.trustprojectcode=b.trustprojectcode and a.objectno=b.objectno");
        System.out.println(sql.toString());
    }

    private static void bussiness_balance_temp(String sDate, String sCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table business_balance_temp NOLOGGING tablespace bhxt_temp as ");
        sql.append("select bc.trustprojectcode,bc.contractno,bc.contractsum,bc.begindate,bc.enddate,bc.certid,");
        sql.append("case when bc.contractsum-nvl(bs.actualcorp1,0)<1 then 0 else bc.contractsum-nvl(bs.actualcorp1,0) end as balance1,");
        sql.append("case when bc.contractsum-nvl(bs.actualcorp2,0)<1 then 0 else bc.contractsum-nvl(bs.actualcorp2,0) end as balance2,");
        sql.append("case when bs.overstatus1=0 and bc.enddate<='").append(sDate).append("' and bc.contractsum>nvl(bs.actualcorp1,0)+1 then 1 ");
        sql.append("     when bc.contractsum-nvl(bs.actualcorp1,0)<1 then 0 else bs.overstatus1  end as status1,");
        sql.append("case when bs.overstatus2=0 and bc.enddate<='").append(sDate).append("' and bc.contractsum>nvl(bs.actualcorp2,0)+1 then 1 ");
        sql.append("     when bc.contractsum-nvl(bs.actualcorp2,0)<1 then 0 else bs.overstatus2  end as status2,");
        sql.append("case when bs.overstatus1=1 then ");
        sql.append("  (case when bs.payplannum>bs.returnnums1 then bs.returnnums1+1 else bs.payplannum end) ");
        sql.append("  else 0 end  as overseqid1,");
        sql.append("case when bs.overstatus2=1 then ");
        sql.append("  (case when bs.payplannum>bs.returnnums2 then bs.returnnums2+1 else bs.payplannum end) ");
        sql.append("  else 0 end  as overseqid2,");
        sql.append("case when bc.enddate<='").append(sDate).append("' and bc.contractsum>nvl(bs.actualcorp1,0) and nvl(bs.payplannum,1)=1 then bc.enddate  else ");
        sql.append("  null end as overdate1,");
        sql.append("case when bc.enddate<='").append(sDate).append("' and bc.contractsum>nvl(bs.actualcorp2,0) and nvl(bs.payplannum,1)=1 then bc.enddate  else ");
        sql.append("  null end as overdate2,-1 as overdays1,-1 as overdays2,");
        sql.append("case when bc.enddate<='").append(sDate).append("' then bc.contractsum else nvl(bs.payprincipalamt,0) end as payprincipalamt,");
        sql.append("nvl(bs.actualcorp1,0) as actualcorp1,");
        sql.append("nvl(bs.actualcorp2,0) as actualcorp2");
        sql.append(" from business_contract bc,(select nvl(a.trustprojectcode,b.trustprojectcode) as trustprojectcode,nvl(a.objectno,b.objectno) as objectno,nvl(a.payplannum,0) as payplannum,a.maxpaydate as maxpaydate,nvl(a.payprincipalamt,0) as payprincipalamt,b.maxoccurdate as maxoccurdate,nvl(b.actualcorp1,0) as actualcorp1,nvl(b.actualcorp2,0) as actualcorp2,nvl(b.returnnums1,0) as returnnums1,nvl(b.returnnums2,0) as returnnums2,case when nvl(a.payplannum,0)>nvl(b.returnnums1,0) and nvl(a.payprincipalamt,0)> nvl(b.actualcorp1,0) then 1 else 0 end as overstatus1,case when nvl(a.payplannum,0)>nvl(b.returnnums2,0) and nvl(a.payprincipalamt,0)> nvl(b.actualcorp2,0) then 1 else 0 end as overstatus2 from (select pst.trustprojectcode,pst.objectno,count(*) as payplannum,max(pst.paydate) as maxpaydate,sum(pst.payprincipalamt) as payprincipalamt from payment_schedule pst where pst.trustprojectcode='ZX12CW' and pst.paydate<='2020/01/11' group by pst.trustprojectcode,pst.objectno) a full join (select pdt.trustprojectcode,pdt.objectno,max(pdt.occurdate) as maxoccurdate,sum(case when pdt.paytype not in ('12', '13', '17', '31', '32') then pdt.actualcorp+nvl(pdt.crosspayserviceprincipal,0) else 0 end) as actualcorp1,sum(case when pdt.paytype not in('10','16','19','20') then pdt.actualcorp+nvl(pdt.crosspayserviceprincipal,0) else 0 end) as actualcorp2,sum(case when pdt.paytype not in ('12', '13', '17', '31', '32') then 1 else 0 end) as returnnums1,sum(case when pdt.paytype not in ('10','16','19','20') then 1 else 0 end) as returnnums2 from payment_detail pdt where pdt.trustprojectcode='ZX12CW' and pdt.occurdate<='2020/01/11' group by pdt.trustprojectcode,pdt.objectno) b on a.trustprojectcode=b.trustprojectcode and a.objectno=b.objectno) bs");
        sql.append(" where bc.trustprojectcode=bs.trustprojectcode(+)");
        sql.append(" and bc.contractno=bs.objectno(+)");
        sql.append(" and bc.trustprojectcode='").append(sCode).append("' and bc.begindate <='").append(sDate).append("'");
        System.out.println(sql.toString());
        sql = new StringBuffer();
        sql.append("CREATE INDEX ").append("bbt").append("_NO ");
        sql.append("ON ").append("business_balance_temp").append(" (trustprojectcode,contractno) tablespace bhxt_temp");
        System.out.println(sql.toString());

        sql = new StringBuffer();
        sql.append("update business_balance_temp set overdate1 = nvl(getpaydate_xzxt(trustprojectcode,contractno,overseqid1),'");
        sql.append(sDate).append("'),overdays1 = to_date('");
        sql.append(sDate).append("','yyyy/mm/dd')-to_date(nvl(getpaydate_xzxt(trustprojectcode,contractno,overseqid1),'");
        sql.append(sDate).append("'),'yyyy/mm/dd') where status1='1'");
        sql.append(" and (overdate1 is null or overdays1=-1)");
        System.out.println(sql.toString());

        sql = new StringBuffer();
        sql.append("update business_balance_temp set overdate2 = nvl(getpaydate_xzxt(trustprojectcode,contractno,overseqid2),'");
        sql.append(sDate).append("'),overdays2=to_date('");
        sql.append(sDate).append("','yyyy/mm/dd')-to_date(nvl(getpaydate_xzxt(trustprojectcode,contractno,overseqid2),'");
        sql.append(sDate).append("'),'yyyy/mm/dd')  where status2='1'");
        sql.append(" and (overdate2 is null or overdays2=-1)");
        System.out.println(sql.toString());
    }
}
