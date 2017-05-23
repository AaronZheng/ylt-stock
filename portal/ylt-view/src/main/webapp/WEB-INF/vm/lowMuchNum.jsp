<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>低位放量</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-table.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrapValidator.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-select.min.css">
    <script src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-table.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrapValidator.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-select.min.js"></script>
    <script type="text/javascript">

       var status = "";
       var currentPageSize = 10;
       var windowPageSize = 10;
       var currentPageNumber = 0;
       var condition;

       function checkStatus(status){
           this.status = status;
       }

       var content = new Array();

       function queryCarrierOrderDetail(row){
           $("#historyModel").modal('show');
           var data = JSON.parse(content[row]);
           var tableContent = "<table class='table'><tr><th>物流公司</th><td>"+data.carrierName+"</td></tr>"+
                              "<tr><th>发货地</th><td>"+data.sendProvinceName+data.sendCityName+data.sendCountyName+data.sendTownName+"</td></tr>"+
                              "<tr><th>收货地</th><td>"+data.deliveredProvinceName+data.deliveredCityName+data.deliveredCountyName+data.deliveredTownName+"</td></tr>"+
                              "<tr><th>时效天数</th><td>"+data.standardRule+"（天）</td></tr>"+
                              "<tr><th>样本总单量</th><td>"+data.num+"</td></tr>"+
                              "<tr><th>样本分布</th><td>"+data.timeClass+"</td></tr></table>"

           document.getElementById("historyTable").innerHTML=tableContent;

       }

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
              url: "<%=request.getContextPath()%>/promiseEffctQuery/queryCarrierTm.json",
              queryParams: function(params) {
                  content = null;
                  return {
                      startRow : params.offset,
                      pageSize : params.limit,
                      sendProvinceId:document.getElementById("sendProvince").value,
                      sendCityId:document.getElementById("sendCity").value,
                      sendCountyId:document.getElementById("sendCounty").value,
                      sendTownId:document.getElementById("sendTown").value,
                      desProvinceId:document.getElementById("desProvince").value,
                      desCityId:document.getElementById("desCity").value,
                      desCountyId:document.getElementById("desCounty").value,
                      desTownId:document.getElementById("desTown").value
                  };
              },
              sidePagination: "server",
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

        function queryIsSignCarrier(){
            var sendCounty = document.getElementById("sendCounty").value;
            if(sendCounty == null || sendCounty == ""){
                alert("发货地址需要选到三级");
                return ;
            }
            var desCounty = document.getElementById("desCounty").value;
            if(desCounty == null || desCounty == ""){
                alert("收货地址需要选到三级");
                return ;
            }
            var sendProvince = document.getElementById("sendProvince").value;
            var sendCity = document.getElementById("sendCity").value;
            var sendTown = document.getElementById("sendTown").value;
            var desProvince = document.getElementById("desProvince").value;
            var desCity = document.getElementById("desCity").value;
            var desTown = document.getElementById("desTown").value;
            $.ajax({
                type: "GET",
                url: "<%=request.getContextPath()%>/promiseEffctQuery/queryCarrierTm.json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {
                    sendProvinceId:sendProvince,
                    sendCityId:sendCity,
                    sendCountyId:sendCounty,
                    sendTownId:sendTown,
                    desProvinceId:desProvince,
                    desCityId:desCity,
                    desCountyId:desCounty,
                    desTownId:desTown,
                    pageSize:currentPageSize
                },
                dataType: "json",
                success: function(data){
                    $("#enableCarrierRouteTable").bootstrapTable('load', data.result);
                }
            });
        }



       function clearData(node){
           if(node == "sendProvince") {
               $("#sendCity").empty();
               $("#sendCity").append("<option value=''>请选择</option>");
               $("#sendCity").selectpicker('render');
               $("#sendCity").selectpicker('refresh');

               $("#sendCounty").empty();
               $("#sendCounty").append("<option value=''>请选择</option>");
               $("#sendCounty").selectpicker('render');
               $("#sendCounty").selectpicker('refresh');

               $("#sendTown").empty();
               $("#sendTown").append("<option value=''>请选择</option>");
               $("#sendTown").selectpicker('render');
               $("#sendTown").selectpicker('refresh');
           }else if(node == "sendCity"){
               $("#sendCounty").empty();
               $("#sendCounty").append("<option value=''>请选择</option>");
               $("#sendCounty").selectpicker('render');
               $("#sendCounty").selectpicker('refresh');

               $("#sendTown").empty();
               $("#sendTown").append("<option value=''>请选择</option>");
               $("#sendTown").selectpicker('render');
               $("#sendTown").selectpicker('refresh');
           }else if(node == "sendCounty"){
               $("#sendTown").empty();
               $("#sendTown").append("<option value=''>请选择</option>");
               $("#sendTown").selectpicker('render');
               $("#sendTown").selectpicker('refresh');
           }else if(node == "desProvince"){
               $("#desCity").empty();
               $("#desCity").append("<option value=''>请选择</option>");
               $("#desCity").selectpicker('render');
               $("#desCity").selectpicker('refresh');

               $("#desCounty").empty();
               $("#desCounty").append("<option value=''>请选择</option>");
               $("#desCounty").selectpicker('render');
               $("#desCounty").selectpicker('refresh');

               $("#desTown").empty();
               $("#desTown").append("<option value=''>请选择</option>");
               $("#desTown").selectpicker('render');
               $("#desTown").selectpicker('refresh');
           }else if(node == "desCity"){
               $("#desCounty").empty();
               $("#desCounty").append("<option value=''>请选择</option>");
               $("#desCounty").selectpicker('render');
               $("#desCounty").selectpicker('refresh');

               $("#desTown").empty();
               $("#desTown").append("<option value=''>请选择</option>");
               $("#desTown").selectpicker('render');
               $("#desTown").selectpicker('refresh');
           }else if(node == "desCounty"){
               $("#desTown").empty();
               $("#desTown").append("<option value=''>请选择</option>");
               $("#desTown").selectpicker('render');
               $("#desTown").selectpicker('refresh');
           }
       }

        function getChildNode(currentNode,childNode){
            var areaId  = document.getElementById(currentNode).value;
            clearData(currentNode);
            $.ajax({
                type: "GET",
                url: "<%=request.getContextPath()%>/carrierRoute/quereyAddressInfo.json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {
                    addressCode : areaId
                },
                dataType: "json",
                success: function(data){
                    var tempAjax;
                    if(data == null || data == "")
                            return;
                    data = JSON.parse(data);
                    tempAjax = "<option value=''>请选择</option>";
                    for(var i = 0; i< data.length; i++)
                        tempAjax += "<option value='"+data[i].areaId+"'>"+data[i].areaName+"</option>";
                    $("#"+childNode+"").empty();
                    $("#"+childNode+"").append(tempAjax);
                    $("#"+childNode+"").selectpicker('render');
                    $("#"+childNode+"").selectpicker('refresh');
                }
            });
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
                    <select class="selectpicker form-control" data-live-search="true" id="sendProvince" style="margin-left: 4px;font-size: smaller;" onchange="getChildNode('sendProvince','sendCity')">
                        <option value="1">低横突</option>
                        <option value="2">震荡突</option>
                    </select>
                        </div>
                </td>
                <td>
                    <div class="input-group" style="margin-left: 10px;" >
                                <span class="input-group-addon" style="font-size: smaller;">范围</span>
                                <select class="selectpicker form-control" data-live-search="true"  id="desProvince" style="margin-left: 4px;" onchange="getChildNode('desProvince','desCity')">
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
                    <input name = "carrierId" id="carrierId"  type="number" style="width: 150px;"  class="form-control" placeholder="计算开始时间">
                </div>
                </td>
                
                <td>
                  <div class="input-group" style="margin-left: 10px;" >
                    <span class="input-group-addon">计算开始时间</span>
                    <input name = "carrierId" id="carrierId"  type="number" style="width: 150px;"  class="form-control" placeholder="计算开始时间">
                </div>
                </td>
                <td>
                  <div class="input-group" style="margin-left: 10px;" >
                    <span class="input-group-addon">计算点</span>
                    <input name = "carrierId" id="carrierId"  type="number" style="width: 150px;"  class="form-control" placeholder="计算开始时间">
                </div>
                </td>
                <td>
                    <button type="button" class="btn btn-primary" style="margin-left: 40px;" onclick="queryIsSignCarrier()">查询</button>
                </td>
                <td></td>
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
</div>




<div class="panel panel-primary"  style="margin-top: 20px; margin-left: 4px; margin-right: 4px">
    <div class="panel-body">
        <table  id="enableCarrierRouteTable"></table>
        </table>
    </div>
</div>

</body>
</html>
