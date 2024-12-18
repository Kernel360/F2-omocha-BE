<#assign site_title_top = 'Omocha-Auction'>
<#assign site_title_content = 'Omocha-Auction'>
<#assign point_color = '#02b875'>

<div style="font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #fd565a; margin: 100px auto; padding: 30px 0; box-sizing: border-box;">
    <h1 style="margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;">
        <span style="font-size: 15px; margin: 0 0 10px 3px;">${site_title_top}</span><br/>
        <span style="color: #fd565a;">메일인증</span> 안내입니다.
    </h1>
    <p style="font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;">
        안녕하세요.<br/>
        ${site_title_content}에 가입해 주셔서 진심으로 감사드립니다.<br/>
        아래 <b style="color: #fd565a;">'인증 코드'</b> 를 복사하여 회원가입을 완료해 주세요.<br/>
        감사합니다.
    </p>

    <a style="color: #FFF; text-decoration: none; text-align: center;" target="_blank">
        <p style="display: inline-block; width: 300px; height: 45px; margin: 30px 5px 40px; background: #fd565a; line-height: 45px; vertical-align: middle; font-size: 16px;">
            ${code}</p>
    </a>


    <div style="border-top: 1px solid #DDD; padding: 5px;">
        <#--            <p style="font-size: 13px; line-height: 21px; color: #555;">-->
        <#--                만약 버튼이 정상적으로 클릭되지 않는다면, 아래 링크를 복사하여 접속해 주세요.<br/>-->
        <#--                &lt;#&ndash;            ${auth_url}&ndash;&gt;-->
        <#--            </p>-->
    </div>
</div>
