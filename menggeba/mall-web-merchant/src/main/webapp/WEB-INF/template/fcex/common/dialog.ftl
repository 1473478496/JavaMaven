  <!-- 通用提示弹框 -->
  <div class="tip_box">
    <div class="tip_title">标题</div>
    <a href="javascript:;" class="tip_close"></a>
    <div class="tip_main">内容</div>

    <div class="tip_footer">
      <button type="button" class="btn btn_yes" id="dialogSureBtn">确认</button>
      <button type="button" class="btn btn_no" id="dialogCloseBtn">取消</button>
    </div>
  </div>
  <div class="tip_bg"></div>

  <script>
  // 弹出弹框
  function tip_fadeIn(dialogType){
      $('.tip_bg').fadeIn();
      $('.tip_box').delay(200).show().animate({"margin-top":"30px","opacity":"1"},200);
      $('.btn_no,.tip_close').click(tip_fadeOut);   //取消
      $('.btn_yes').click(function(){   //确认
        tip_fadeOut ();
      });
      switch (dialogType) {   //dialogType的取值0,1,2,3
      case 0:
        $('.tip_title').text('提示');
        break;
      case 1:
        $('.tip_title').text('警告');
        break;
      case 2:
        $('.tip_title').text('成功');
        $('.btn_no').hide();
        break;
      case 3:
        $('.tip_title').text('错误');
        $('.btn_no').hide();
        break;
      default:
        $('.tip_title').text('提示')
      };

  }
  // 关闭弹框
  function tip_fadeOut(){
      $('.tip_box').animate({"margin-top":"0px","opacity":"0"},200,function(){$('.tip_box').hide()});
      $('.tip_bg').delay(100).fadeOut();
  }
  </script>
  