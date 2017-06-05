<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>低位放量</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-table.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrapValidator.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-datepicker.min.css">
    <script src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-table.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrapValidator.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-select.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-datepicker.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-datepicker.zh-CN.min.js"></script>

    <script type="text/javascript">
       var status = "";
       var currentPageSize = 10;
       var windowPageSize = 10;
       var currentPageNumber = 0;
       var condition;

       function checkStatus(status){
           this.status = status;
       }
       
       Date.prototype.Format = function (fmt) { //author: meizz 
    	    var o = {
    	        "M+": this.getMonth() + 1, //月份 
    	        "d+": this.getDate(), //日 
    	        "h+": this.getHours(), //小时 
    	        "m+": this.getMinutes(), //分 
    	        "s+": this.getSeconds(), //秒 
    	        "S": this.getMilliseconds() //毫秒 
    	    };
    	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    	    for (var k in o)
    	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    	    return fmt;
    	}


       $(document).ready(function(){
     	  
   		  var calcDate = new Date();
        //  document.getElementById("calcDate").value=calcDate.Format("yyyy-MM-dd");
    	   
     	  $(".form_date").datepicker({
               language: "zh-CN",
               autoclose: true,//选中之后自动隐藏日期选择框
               clearBtn: true,//清除按钮
               todayBtn: true,//今日按钮
               endDate:new Date(),
               todayHighlight:true,
               initialDate:calcDate.Format("yyyy-MM-dd"),
               format: "yyyy-mm-dd"
           });
     	  
     
     	  
       });

      $(document).ready(function(){
    	  
          $('#enableCarrierRouteTable').bootstrapTable({
              method: 'get',
              height : 400,
              striped: true,
              cache: false,
              pagination: true,
              onPageChange : function (number,size) {
                  currentPageSize = size;
                  return{
                      number : number,
                      size : size
                  };
              },
              sortable: true,
              sortOrder: "asc",
              pageSize: 10,
              pageList: [10, 25, 50, 100],
              sidePagination: "client",
              search: false,
              strictSearch: true,
              responseHandler : function(res) {
                  return res.result;
              },
              idField : "id",
              columns: [{
                  field: 'carrierId',
                  title: '股票代码'
              },{
                  field: 'carrierName',
                  title: '股票名称'
              },{
                  field: 'content',
                  title: '计算点昨日交易价'
               },{
                   field: 'content',
                   title: '计算点交易价'
                },{
                  field: 'standardRule',
                  title: '计算点昨日交易量'
              },{
                  field: 'calcDate',
                  title: '计算点交易量'
              },{
                  field: 'calcDate',
                  title: '差距倍数'
              }]
          });

      });

        function queryLowNum(){
            var numType = document.getElementById("numType").value;
            var stockType = document.getElementById("stockType").value;
            var priceRange = document.getElementById("priceRange").value;
            var variance = document.getElementById("variance").value;
            var numTimes = document.getElementById("numTimes").value;
            var startTime = document.getElementById("startTime").value;
            var calcDate = document.getElementById("calcDate").value;
            
            var range = priceRange.split("-");
            $.ajax({
                type: "GET",
                url: "<%=request.getContextPath()%>/lowManyNum/queryLowManyNum.json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {
                	numType:numType,
                	stockType:stockType,
                	lowPrice:range[0],
                	variance:variance,
                	hightPrice:range[1],
                	times:numTimes,
                	startTime:startTime,
                	calcDate:calcDate
                },
                dataType: "json",
                success: function(data){
                    $("#enableCarrierRouteTable").bootstrapTable('load', data.result);
                }
            });
        }


        
        function openCal(){
        	$('.form_date').datepicker('show');
        }
        
        function removeCal(){
        	$('.form_date').datepicker('remove');
        }
        
    </script>
    <style>
        .table {
            font-size: smaller;
        }
    </style>
</head>
<body>
<div class="panel panel-primary" style="margin-top: 10px; margin-left: 4px; margin-right: 4px;">
    <div class="panel-heading" >查询条件</div>
    <div class="panel-body" style="font-size: smaller">
        <table>
            <tr>
                <td>
                 <div class="input-group">
                    <span class="input-group-addon" style="font-size: smaller">放量方式</span>
                    <select class="selectpicker form-control" data-live-search="true" id="numType" style="margin-left: 4px;font-size: smaller;">
                        <option value="1">低横突</option>
                        <option value="2">震荡突</option>
                    </select>
                        </div>
                </td>
                <td>
                    <div class="input-group" style="margin-left: 10px;" >
                        <span class="input-group-addon" style="font-size: smaller;">范围</span>
                        <select class="selectpicker form-control" data-live-search="true"  id="stockType" style="margin-left: 4px;" >
                            <option value="1">沪深主</option>
                            <option value="2">沪A</option>
                            <option value="3">沪主</option>
                            <option value="4">全部</option>
                        </select>
                    </div>
                </td>
                
                 <td>
	                <div class="input-group" style="margin-left: 10px;" >
	                    <span class="input-group-addon">价格区间</span>
	                    <input name = "priceRange" id="priceRange"  type="text" style="width: 80px;" value="10-40"  class="form-control">
	                </div>
                </td>
                
                  <td>
	                <div class="input-group" style="margin-left: 10px;" >
	                    <span class="input-group-addon">放量倍数</span>
	                    <input name = "numTimes" id="numTimes" value="4"  type="number" style="width: 80px;" value="10-40"  class="form-control">
	                </div>
                 </td>
                 
                 <td>
	                <div class="input-group" style="margin-left: 10px;" >
	                    <span class="input-group-addon">方差</span>
	                    <input name = "variance" id="variance"  type="number" value="0.5" style="width: 80px;" value="10-40"  class="form-control">
	                </div>
                 </td>
                
                <td>
                  <div class="input-group" style="margin-left: 10px;">  
                            <div class="input-group date form_date col-md-3" data-date="" data-date-format="yyyy年mm月dd日" data-link-field="dtp_input2" data-link-format="yyyy年mm月dd日" >  
                                <span class="input-group-addon">开始计算</span>
                                <input id="startTime" class="form-control" style="width: 120px;" readonly="true"/>  
                            </div>  
                        </div>  
                </td>
                <td>
                  <div class="input-group" style="margin-left: 10px;">
                      <div class="input-group date form_date col-md-3" data-date="" data-date-format="yyyy年mm月dd日" data-link-field="dtp_input2" data-link-format="yyyy年mm月dd日" >  
                          <span class="input-group-addon">计算点</span>
                          <input id="calcDate" class="form-control" style="width: 120px;" readonly="true"/>  
                      </div>  
                </div>
                </td>
                <td>
                    <button type="button" class="btn btn-primary" style="margin-left: 20px;" onclick="queryLowNum()">查询</button>
                </td>
            </tr>
        </table>
    </div>
</div>


<div class="modal fade dialog-lg" id="historyModel"  tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="false">
    <div class="modal-dialog" style="width: 900px;">
        <div class="modal-content" >
            <div class="modal-header" style="background-color: #def2ff">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title"  id="myModalLabel">
                   物流时效详情
                </h4>
            </div>
            <div class="modal-body">
                <div  id="historyTable"></div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>




<div class="panel panel-primary"  style="margin-top: 20px; margin-left: 4px; margin-right: 4px">
    <div class="panel-body">
        <table  id="enableCarrierRouteTable"></table>
        </table>
    </div>
</div>

</body>
</html>
