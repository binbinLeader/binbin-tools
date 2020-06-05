package com.binbin.tools;

import com.binbin.utils.DateUtils2;

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

//        System.out.println(amsSql());
//        System.out.println(absSql());
//        System.out.println(trustProjectSql());
//        System.out.println(loanRepaySheduleSql());
//        System.out.println(loanRepaySql());
//        System.out.println(loanContractSql());
//        printOverdaySql();
//        loanRepaySql();
        stringBuffer2String();

    }

    /**
     * @Description: 个人贷款回款信息入库
     * @author: LiYanxiang
     * @time: 2020年2月26日
     */
    private static String stringBuffer2String() {
        String hourMinSs = DateUtils2.getToday("hh:mm:ss");
        String occurDate = "2020/03/26";
        String strDate = "2020-04-22";
        String Code = "PPD";
        String relativeMonth = "2019/12/08";
        String trustProjectCode = "ZX12CW";
        String statDate = DateUtils2.getToday("yyyy-MM-dd HH:mm:ss");
        String statDate2 = statDate.substring(0, 7);

        String sql = "insert into req_statistic_month (statdate, trustprojectcode, totalcount, validcount, inputtime) "
                + " select to_char(reqinfo.query_time, 'yyyy-mm'), "
                + "        reqinfo.trust_project_code, "
                + "        count(1), "
                + "        ( select count(1) "
                + "          from BHXT_REQ_INFO_2G reqinfo2 "
                + "          where reqinfo2.report_no is not null "
                + "          and reqinfo2.trust_project_code = reqinfo.trust_project_code "
                + "          and to_char(reqinfo2.query_time, 'yyyy-mm') = to_char(reqinfo.query_time, 'yyyy-mm') ),' "
                +           statDate
                +"' from BHXT_REQ_INFO_2G reqinfo "
                + " where reqinfo.trust_project_code <> '10050' "
                + " and to_char(reqinfo.query_time, 'yyyy-mm') < '"+statDate2
                +"' and not exists "
                + "     (select 1 "
                + "      from req_statistic_month rsm "
                + "      where rsm.trustprojectcode = reqinfo.trust_project_code "
                + "      and rsm.statdate = to_char(reqinfo.query_time, 'yyyy-mm')) "
                + "      group by reqinfo.trust_project_code, to_char(reqinfo.query_time, 'yyyy-mm') ";

        System.err.println(sql.toString());
        return sql.toString();
    }

    /**
     * @Description: 个人贷款回款信息入库
     * @author: LiYanxiang
     * @time: 2020年2月26日
     */
    private static String loanRepaySql() {
        String occurDate = "2020/04/22";
        String relativeMonth = "2019/12/08";
        String trustProjectCode = "ZX12CW";
        String hourMinSs = DateUtils2.getToday("hh:mm:ss");
        StringBuffer sql = new StringBuffer();
        sql.append("select case when contractsum-nvl(actualpaysum,0)<0 then 0 else contractsum-actualpaysum end as balance,");
        sql.append(" ContractNo,OCCURDATE from (");
        sql.append(" select * from (select contractsum,ContractNo");
        sql.append(" from business_contract where TrustProjectCode='").append(trustProjectCode).append("'");
        sql.append(" and balance>0 ) y1,(select sum(ACTUALCORP+nvl(CROSSPAYSERVICEPRINCIPAL,0)) as actualpaysum,");
        sql.append(" objectno as ContractNo2,max(OCCURDATE) as OCCURDATE");
        sql.append(" from payment_detail where trustprojectcode='").append(trustProjectCode).append("'");
        sql.append(" and PAYTYPE not in ('12','13','14','17','31','32')");
        sql.append(" group by objectno) y2 where y1.ContractNo=y2.ContractNo2) y");
        System.err.println(sql.toString());
        return sql.toString();
    }

    private static void printOverdaySql() {
        String trustProjectCode = "ZX12CW";

        StringBuffer sql = new StringBuffer();
        sql.append("update BUSINESS_CONTRACT set OVERDUEDAYS=0,OUROVERDUESTATUS=0");
        sql.append(" where TRUSTPROJECTCODE='").append(trustProjectCode).append("'");
        sql.append(" and OUROVERDUESTATUS='1'");
        sql.append(" and CONTRACTNO not in (");

        sql.append("select objectno");
        sql.append(" from business_over t ");
        sql.append(" where t.trustprojectcode = '").append(trustProjectCode).append("' ");
        sql.append(" and filedate in (select max(os.filedate) ");
        sql.append(" from over_status os ");
        sql.append(" where os.trustprojectcode = '").append(trustProjectCode).append("' ");
        sql.append(")");
        sql.append(")");
        System.out.println(sql.toString());

    }

    /**
     * @Description: 个人贷款信息入库
     * @author: LiYanxiang
     * @time: 2020年2月26日
     */
    private static String loanContractSql() {
        String occurDate = "2020/04/22";
        String relativeMonth = "2019/12/08";
        String trustProjectCode = "ZX12CW";
        String hourMinSs = DateUtils2.getToday("hh:mm:ss");
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ods_pa_pers_loan_contract_dt");
        sql.append(" select");
        sql.append(" bc.CONTRACTNO");
        sql.append(" ,bc.CONTRACTNO"); // 借贷合同号
        sql.append(" ,'1'"); // 五级分类代码
        sql.append(" ,'").append(occurDate.replaceAll("/", "")).append("'"); // 五级分类认定日期
        sql.append(" ,'D1'"); // 借贷账户类型代码
        sql.append(" ,'0'"); // 资产转让标志代码
        sql.append(" ,'110000'"); // 业务申请地行政区划代码
        sql.append(" ,'4'"); // 业务经营类型代码
        sql.append(" ,bc.CONTRACTNO"); // 授信申请编号
        sql.append(" ,bc.CONTRACTSUM"); // 信用额度金额
        sql.append(" ,'CNY'"); // 币种
        sql.append(" ,info.CUSTOMERID"); // 客户号
        sql.append(" ,'0'"); // 分次放款标志代码
        sql.append(" ,'4'"); // 担保方式代码
        sql.append(" ,'10'"); // 行业类别代码
        sql.append(" ,'10.4'"); // 行业类别明细代码
        sql.append(" ,(select sum(PAYINTEAMT) from payment_schedule ps where ps.objectno = bc.CONTRACTNO)"); // 利息
        sql.append(" ,'1'"); // 计息方式代码
        sql.append(" ,'5'"); // 付息方式代码
        sql.append(" ,bc.BUSINESSRATE"); // 利率水平

        sql.append(" ,'1'"); // 利率类别代码
        sql.append(" , case when bc.OUROVERDUESTATUS='1' then '1' else '0' end"); // 是否存在逾期代码
        sql.append(" ,'1'"); // 利率是否固定代码
        sql.append(" ,bc.CONTRACTSUM"); // 贷款金额

        // 利率计算 0.3600 10-年利率；20-月利率；30-日利率。
        sql.append(" ,(select case when bt.INTERESTRATETYPE='10' then ");
        sql.append(" (0 || decode(bt.BUSINESSRATE*0.01, 0, '0.0000',(to_char(round(bt.BUSINESSRATE*0.01,4),'fm99999999999999.0000'))) ) ");
        sql.append(" when INTERESTRATETYPE='20' then ");
        sql.append(" (0 || decode(bt.BUSINESSRATE*0.01*12, 0, '0.0000',(to_char(round(bt.BUSINESSRATE*0.01*12,4),'fm99999999999999.0000'))) )");
        sql.append(" when INTERESTRATETYPE='30' then ");
        sql.append(" (0 || decode(bt.BUSINESSRATE*0.01*365, 0, '0.0000',(to_char(round(bt.BUSINESSRATE*0.01*365,4),'fm99999999999999.0000'))) )");
        sql.append(" end ");
        sql.append(" from batch_temporary bt ");
        sql.append(" where bt.TRUSTPROJECTCODE=bc.TRUSTPROJECTCODE");
        sql.append(" and bt.CONTRACTNO=bc.contractno) as loan_annual_rate"); // 贷款年化利率

        sql.append(" ,'1'"); // 借贷业务大类代码
        sql.append(" ,'91'"); // 借贷业务种类细分代码
        sql.append(" ,replace(bc.ENDDATE,'/','')"); // 贷款合同结束日期
        sql.append(" ,replace(bc.BEGINDATE,'/','')"); // 贷款合同生效日期
        sql.append(" ,replace(bc.ENDDATE,'/','')"); // 贷款到期日期
        sql.append(" ,''"); // 贷款展期到期日期
        sql.append(" ,'1'"); // 贷款发放形式代码
        sql.append(" ,''"); // 贷款投向代码
        sql.append(" ,replace(bc.BEGINDATE,'/','')"); // 贷款发放日期
        sql.append(" ,'F0219'"); // 贷款产品类别代码
        sql.append(" , case when bc.BALANCE=0 then 'FS04' else 'FS01' end"); // 贷款状态代码
        sql.append(" ,'1'"); // 贷款种类代码
        sql.append(" ,'10'"); // 贷款用途代码
        sql.append(" ,bc.CUSTOMERNAME"); // 借款人姓名
        sql.append(" ,tp.COOPERATION"); // 机构代码
        sql.append(" ,'0'"); // 其他还款保证方式代码
        sql.append(" ,tp.TRUSTPROJECTCODE"); // 产品代码
        sql.append(" ,bc.CONTRACTNO"); // 贷款借据号
        sql.append(" ,'3'"); // 还款频率代码
        sql.append(" ,'11'"); // 还款方式代码
        sql.append(" ,bc.DEADLINE"); // 还款期数
        sql.append(" ,'4'"); // 消费贷款风险管理方式代码
        sql.append(" ,'").append(occurDate.replaceAll("/", "-")).append(" ").append(hourMinSs).append("'");
        sql.append(" ,'").append(occurDate.replaceAll("/", "-")).append(" ").append(hourMinSs).append("'");
        sql.append(" ,'").append(occurDate.replaceAll("/", "")).append("'");
        sql.append(" from");
        sql.append(" business_contract bc,trust_project tp, IND_INFO info");
        sql.append(" where");
        sql.append(" bc.TRUSTPROJECTCODE = tp.TRUSTPROJECTCODE");
        sql.append(" and bc.CERTID = info.CERTID");
        sql.append(" and bc.TRUSTPROJECTCODE = '").append(trustProjectCode).append("'");
        sql.append(" and bc.begindate >= '").append(relativeMonth).append("'");
        sql.append(" and bc.begindate <= '").append(occurDate).append("'");
        sql.append(" and not exists (");
        sql.append(" select t.LOAN_CONTRACT_NUM from ods_pa_pers_loan_contract_dt t where t.LOAN_CONTRACT_NUM = bc.CONTRACTNO");
        sql.append(" )");
        return sql.toString();
    }

    /**
     * 个人贷款回款信息入库-由还款计划为主表来看是否逾期
     * @return
     */
    private static String loanRepaySheduleSql() {
        String occurDate = "2020/04/06";
        String trustProjectCode = "ZX12CW";
        String hourMinSs = DateUtils2.getToday("hh:mm:ss");
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ods_pa_pers_loan_repay_dt ");
        sql.append(" select ");
        sql.append(" ps.serialno,");
        sql.append(" ps.objectno,"); // 借贷合同号
        sql.append(" 0,"); // 实际还手续费
        sql.append(" replace(pd.OCCURDATE, '/', ''),"); // 实际还款日期
        sql.append(" 0,"); // 实还违约金
        sql.append(" 0,"); // 实还利息
        sql.append(" 0,"); // 实还其他费用
        sql.append(" 0,"); // 实还罚息
        sql.append(" 0,"); // 实还本金
        sql.append(" ps.SEQID,"); // 当前分期号
        sql.append(" '',"); // 序号
        sql.append(" (ps.PAYPRINCIPALAMT + ps.PAYINTEAMT),"); // 本期余额
        sql.append(" case when (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) >=1 and (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) <=30 then '1'");
        sql.append(" when (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) >=31 and (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) <=60 then '2'");
        sql.append(" when (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) >=61 and (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) <=60 then '3'");
        sql.append(" when (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) >=91 and (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) <=60 then '4'");
        sql.append(" when (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) >=121 and (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) <=60 then '5'");
        sql.append(" when (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) >=151 and (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) <=60 then '6'");
        sql.append(" when (to_date('").append(occurDate).append("','yyyy/mm/dd') -to_date(bo.FILEDATE,'yyyy/mm/dd')) >180  then '7'");
        sql.append(" end,"); // 当前还款状态代码
        sql.append(" bc.CUSTOMERID,"); // 客户号
        sql.append(" '1',"); // 是否逾期代码
        sql.append(" tp.COOPERATION,"); // 机构代码
        sql.append(" replace(ps.paydate, '/', ''),"); // 逾期日期
        sql.append(" bc.DEADLINE,"); // 计划还款期数
        sql.append(" tp.TRUSTPROJECTCODE,"); // 产品代码
        sql.append(" (bc.DEADLINE - ps.SEQID),"); // 剩余还款期数
        sql.append(" replace(ps.PAYDATE, '/', ''),"); // 应还款日期
        sql.append(" ps.PAYOPERATIONFEE,"); // 应还手续费
        sql.append(" 0,"); // 应还违约金
        sql.append(" ps.PAYINTEAMT,"); // 应还利息
        sql.append(" ps.PAYOTHERFEE1,"); // 应还其他费用
        sql.append(" ps.PAYFINEAMT,"); // 应还罚息
        sql.append(" ps.PAYPRINCIPALAMT,"); // 应还本金
        sql.append(" '',"); // 特殊交易类型代码
        sql.append(" '").append(occurDate.replaceAll("/", "-")).append(" ").append(hourMinSs).append("'"); // 原系统数据创建时间
        sql.append(" ,'").append(occurDate.replaceAll("/", "-")).append(" ").append(hourMinSs).append("'"); // 原系统数据修改时间
        sql.append(" ,'").append(occurDate.replaceAll("/", "")).append("'"); // 业务日期分区字段

        sql.append(" from payment_detail pd,");
        sql.append(" payment_schedule ps,");
        sql.append(" trust_project tp,");
        sql.append(" business_contract bc,");
        sql.append(" business_over bo");
        sql.append(" where pd.trustprojectcode(+) = ps.trustprojectcode and pd.objectno(+) = ps.objectno and pd.seqid(+) = ps.seqid");
        sql.append(" and ps.TRUSTPROJECTCODE = tp.TRUSTPROJECTCODE");
        sql.append(" and ps.objectno = bc.CONTRACTNO");
        sql.append(" and ps.objectno = bo.OBJECTNO");

        sql.append(" and ps.paydate = '").append(occurDate).append("'");
        sql.append(" and ps.trustprojectcode = '").append(trustProjectCode).append("'");
        sql.append(" and pd.serialno is null ");

        sql.append(" and not exists (");
        sql.append(" select t.LOAN_CONTRACT_NUM from ods_pa_pers_loan_repay_dt t where t.LOAN_CONTRACT_NUM = ps.objectno");
        sql.append(" and t.seq_num = ps.seqid");
        sql.append(" )");
        return sql.toString();
    }

    private static String trustProjectSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT TRUSTPROJECTID,TRUSTPROJECTCODE,COOPERATION,PRODUCTENDDATE,TRUSTPROJECTNAME,");
        sql.append("PROJECTSHORTNAME,TRUSTSTARTDATE,TRUSTSCALE,INPUTDATE,UPDATEDATE ");
        sql.append("FROM trust_project WHERE trustprojectcode='").append("ZX12CW").append("' and truststartdate='").append("2020-01-11").append("'");
        return sql.toString();
    }

    private static String absSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT substring(order_time, 1, 10) as order_time,out_trade_no,contract_no,org_code,risk_trade_no,order_status,trust_project_code,customer_name,");
        sql.append("cert_type,cert_id,phone,coin_code,order_amount,account_no,bank_code,");
        sql.append("trade_time,send_time,created_time,updated_time FROM trade_payment_info ");
        sql.append("WHERE trust_project_code = '").append("ZX12CW").append("'");
        sql.append(" and order_status = '00' ");
        sql.append(" AND substring(order_time, 1, 10) = '").append("2020-01-11").append("'");
        return sql.toString();
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
