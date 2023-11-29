# cs211-641-project

## รายชื่อสมาชิก
* 6410451245 พุธิตา ไพบูลย์ธนสมบัติ (bowly01)
  - 10.ความสวยงามและประสบการณ์ของผู้ใช้ 
 b.Graphic User Interface (GUI) มีรูปแบบที่เข้าใจง่าย
 e.(Extra 10 คะแนน) ผู้ใช้โปรแกรมสามารถปรับเปลี่ยน Theme ของ Application ได้
  - 12.โปรแกรมต้องบันทึกค่าของข้อมูลต่าง ๆ ในรูปแบบของไฟล์ csv (comma-separated-values) หรือ json (JavaScript Object Notation) และสามารถโหลดไฟล์ที่บันทึกไว้นั้นมาแสดงผลในโปรแกรมได้อย่างถูกต้อง
  - 17.(extra 5 คะแนน) มีเมนูจัดการหมวดหมู่ของวัสดุหรือครุภัณฑ์
  - 20.ระบบของเจ้าหน้าที่
  - 21.การสร้างบัญชีของผู้ใช้ระบบ
  - 22.ระบบของผู้ใช้ระบบ
    
* 6410451512 หทัยชนก กลัดงาม (HataichanokKladngam)
  - 10.ความสวยงามและประสบการณ์ของผู้ใช้
  b.Graphic User Interface (GUI) มีรูปแบบที่เข้าใจง่าย
  - 12.โปรแกรมต้องบันทึกค่าของข้อมูลต่าง ๆ ในรูปแบบของไฟล์ csv (comma-separated-values) หรือ json (JavaScript Object Notation) และสามารถโหลดไฟล์ที่บันทึกไว้นั้นมาแสดงผลในโปรแกรมได้อย่างถูกต้อง
  - 16.ระบบของผู้ดูแลระบบ ระบบส่วนนี้ถูกจำกัดสิทธิ์สำหรับผู้ดูแลระบบเท่านั้น ผู้ใช้ที่ไม่ใช่ผู้ดูแลระบบต้องไม่สามารถเข้าใช้งานส่วนนี้
  - 19.บัญชีผู้ใช้ของเจ้าหน้าที่
  - 20.ระบบของเจ้าหน้าที่
   

* 6410451521 หทัยทิพย์ แก้วคำ (HathaithipKaewkam)
  - 10.ความสวยงามและประสบการณ์ของผู้ใช้
  b.Graphic User Interface (GUI) มีรูปแบบที่เข้าใจง่าย
  - 12.โปรแกรมต้องบันทึกค่าของข้อมูลต่าง ๆ ในรูปแบบของไฟล์ csv (comma-separated-values) หรือ json (JavaScript Object Notation) และสามารถโหลดไฟล์ที่บันทึกไว้นั้นมาแสดงผลในโปรแกรมได้อย่างถูกต้อง
  - 16.ระบบของผู้ดูแลระบบ ระบบส่วนนี้ถูกจำกัดสิทธิ์สำหรับผู้ดูแลระบบเท่านั้น ผู้ใช้ที่ไม่ใช่ผู้ดูแลระบบต้องไม่สามารถเข้าใช้งานส่วนนี้
  - 19.บัญชีผู้ใช้ของเจ้าหน้าที่
  - 20.ระบบของเจ้าหน้าที่
  - 21.การสร้างบัญชีของผู้ใช้ระบบ
  - 22.ระบบของผู้ใช้ระบบ

## วิธีการติดตั้งหรือรันโปรแกรม
### สำหรับ window
* ให้เข้าไปที่ [Releases](https://github.com/CS211-652/cs211-652-project-untitledteam/releases/tag/CS211-652)
* เข้า Directory -->“cs211-652-project-untitledteam"
* กดเปิดไฟล์ untitledTeam-1.0.0-shaded
### สำหรับ mac os
* ให้โหลดไฟล์ zip จากโปรเจค
* สามารถรันด้วยคำสั่ง `sh run.sh`



## การวางโครงสร้างไฟล์
- database
    - account.csv
    - borrowHistory.csv
    - category.csv
    - HistoryMaterial.csv 
    - material.csv
    - product.csv
- image 
    - products 
      - camera.png
      - cart.png
      - chainsaw.png
      - chair.png
      - dvdPlayer.png
      - Fan.png
      - fixedLadder.png
      - hammer.png
      - Ipad.png
      - loudspeakers.png
      - microphone.png
      - notebook.png
      - plug.png
      - projector.png
      - radio.png
      - Scanner.png
      - studioLights.png
      - table.png
      - tripod.png
      - UPS.png
      - vacuumCleaner.png
    - profiles
      - profile-user.png
      - tor.png
      - yoona.png
    
- src
  - main
    - java
      - ku
        - project
          - controllers
            - AddMaterialController
            - AddProductController
            - DetailProductStaffController
            - DetailsMaterialController
            - HelloController
            - HomeShowProductController
            - HowtouseController
            - ItemController
            - LoginController
            - MainAdminController
            - MaterialListController
            - NewStaffController
            - PasswordController
            - RegisterController
            - RequisitionMaterialController
            - StaffMainController
            - untitledTeamController
            - UserprofileController
          - filter
            - CategoryProductFilter
            - IsBorrowProductFilter
            - ProductFilter
            - ProductStatusFilter
            - SearchProductFilter
          - models
            - account
              - Account
              - AccountList
            - material
              - Category
              - CategoryList
              - History
              - HistoryList
              - Material
              - MaterialList
              - PassTo
            - products
              - Lender
              - Product
              - ProductList
            - user
              - Borrow
              - BorrowList
          - services
              - AccountDataSource
              - AccountDateTimeComparator
              - BorrowDataSource
              - CategoryDataSource
              - DataSource
              - FileService
              - FXRouter
              - HandleImage
              - HistoryDataSource
              - MaterialFileDataSource
              - ProductDataSrc
          - untitledTeam
              - Main 
    - resources
      - images
        - bow.jpg
        - Earth.jpg
        - newstaff.png
        - newuser.png
        - password.png
        - pin.png
        - product.png
      - ku
        - project
          - asset
            - icons
              - icon-addCamera.png
              - icon-Catalog.png
              - icon-com.png
              - icon-go-back.png
              - icon-hammer.png
              - icon-logout.png
              - icon-menubar.png
              - icon-office.png
              - icon-promote.png
              - icon-search.png
              - icon-user.png
              - icon-Walkie.png
              - logo.png
          - styles
            - darkMode.css
            - lightMode.css
            - style.css
          - views
            - addMaterial.fxml 
            - addProduct.fxml
            - adminMain.fxml
            - detailProductStaff.fxml
            - detailsMaterial.fxml
            - hello-view.fxml
            - howToUse.fxml
            - Item.fxml
            - login.fxml
            - materialList.fxml
            - newstaff.fxml
            - password.fxml
            - register.fxml
            - requistionMaterial.fxml
            - showProduct.fxml
            - staffMain.fxml
            - untitledTeam.fxml
            - userProfile.fxml
        
## ตัวอย่างข้อมูลผู้ใช้ระบบ
* (Admin),(admin1),(123456)
* (Admin),(admin2),(123456)
* (Staff),(Boat12),(Boat123)
* (Staff,(Prayut),(123456)
* (Staff,(putita),(putita45)
* (User),(Prawit),123456)
* (User),(Ploy12),(Ploy123)
* (User),(Ryu1234),(Ryu1234)
* (User),(Chutterza),(123456)



## สรุปสิ่งที่พัฒนาแต่ละครั้งที่นำเสนอความก้าวหน้าของระบบ
* ครั้งที่ 1 (13 มกราคม 2023)
    * (ออกแบบ login,register,changePassWord) (หทัยทิพย์ แก้วคำ)
    * (ออกแบบ หน้าโชว์ครุภัณฑ์ของ user,หน้าโปรไฟล์ user) (พุธิตา ไพบูลย์ธนสมบัติ)
    * (ออกแบบ หน้า admin,หน้าโชว์ครุภัณฑ์ของ staff) (หทัยชนก กลัดงาม)
* ครั้งที่ 2 (3 กุมภาพันธ์ 2023)
    * (ออกแบบ csv) (หทัยทิพย์ แก้วคำ)
    * (หน้าหลักแสดงครุภัณฑ์) (พุธิตา ไพบูลย์ธนสมบัติ)
    * (หน้าหลักแสดงวัสดุและแสดงรายละเอียด) (หทัยชนก กลัดงาม)
* ครั้งที่ 3 (24 กุมภาพันธ์ 2023)
    * (สร้างบัญชีเจ้าหน้าที่,สร้างบัญชีใหม่,login,เปลี่ยนรหัสผ่าน) (หทัยทิพย์ แก้วคำ)
    * (ฟีเจอร์ค้นหาครุภัณฑ์,แสดงครุภัณฑ์เป็นหมวดหมู่) (พุธิตา ไพบูลย์ธนสมบัติ)
    * (แสดงข้อมูลของ staff/user ในหน้าหลัก admin,แสดงข้อมูลวัสดุ) (หทัยชนก กลัดงาม)
