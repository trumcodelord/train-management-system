package trainmanagementdemo;

import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TrainManagementDemo extends Application {

    private Stage primaryStage;

    private TableView<VeTau> tableVe;
    private TextField txtGaDi;
    private TextField txtGaDen;
    private TextField txtTenKhach;
    private TextField txtCCCD;
    private TextArea txtHoaDon;

    private ObservableList<VeTau> danhSachVe = FXCollections.observableArrayList();
    private ObservableList<Tau> danhSachTau = FXCollections.observableArrayList();
    private ObservableList<LichTrinh> danhSachLichTrinh = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        taoDuLieuMau();

        Scene welcomeScene = taoManHinhChao();

        primaryStage.setTitle("Hệ thống quản lý đường sắt thông minh");
        primaryStage.setScene(welcomeScene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(650);
        primaryStage.show();
    }

    private Scene taoManHinhChao() {
        Label lblTitle = new Label("HỆ THỐNG QUẢN LÝ ĐƯỜNG SẮT THÔNG MINH");
        lblTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #0B3D91;");

        Label lblHello = new Label("Xin chào! Chúc bạn một ngày làm việc hiệu quả.");
        lblHello.setStyle("-fx-font-size: 18px; -fx-text-fill: #333333;");

        Label lblSub = new Label("Bài tập lớn - Phân tích thiết kế hệ thống");
        lblSub.setStyle("-fx-font-size: 15px; -fx-text-fill: #666666;");

        Button btnStart = new Button("Bắt đầu");
        btnStart.setStyle("-fx-font-size: 16px; -fx-background-color: #0B74DE; -fx-text-fill: white; -fx-padding: 10 30 10 30;");
        btnStart.setOnAction(e -> primaryStage.setScene(taoManHinhChinh()));

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #EAF6FF, #FFFFFF);");
        root.getChildren().addAll(lblTitle, lblHello, lblSub, btnStart);

        return new Scene(root);
    }

    private Scene taoManHinhChinh() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F4F8FB;");

        root.setTop(taoHeader());
        root.setLeft(taoMenuTrai());
        root.setCenter(taoNoiDungBanVe());

        return new Scene(root);
    }

    private HBox taoHeader() {
        Label lblTitle = new Label("TrainManagement - Demo 5 phân hệ lõi");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(12, 20, 12, 20));
        header.setStyle("-fx-background-color: #0B3D91;");
        header.getChildren().add(lblTitle);

        return header;
    }

    private VBox taoMenuTrai() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(15));
        menu.setPrefWidth(210);
        menu.setStyle("-fx-background-color: #DDEBFA;");

        Label lblMenu = new Label("CHỨC NĂNG");
        lblMenu.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnBanVe = taoNutMenu("Quản lý bán vé");
        Button btnTau = taoNutMenu("Quản lý tàu");
        Button btnLichTrinh = taoNutMenu("Quản lý lịch trình");
        Button btnNhanVien = taoNutMenu("Quản lý nhân viên");
        Button btnThuChi = taoNutMenu("Thống kê thu chi");

        btnBanVe.setOnAction(e -> doiNoiDung(taoNoiDungBanVe()));
        btnTau.setOnAction(e -> doiNoiDung(taoNoiDungQuanLyTau()));
        btnLichTrinh.setOnAction(e -> doiNoiDung(taoNoiDungQuanLyLichTrinh()));
        btnNhanVien.setOnAction(e -> thongBaoChucNangDangMoPhong("Quản lý nhân viên"));
        btnThuChi.setOnAction(e -> thongBaoChucNangDangMoPhong("Thống kê thu chi"));

        menu.getChildren().addAll(lblMenu, btnBanVe, btnTau, btnLichTrinh, btnNhanVien, btnThuChi);

        return menu;
    }

    private void doiNoiDung(Node node) {
        BorderPane root = (BorderPane) primaryStage.getScene().getRoot();
        root.setCenter(node);
    }

    private Button taoNutMenu(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10; -fx-background-color: white; -fx-border-color: #AFC7E8;");
        return btn;
    }

    private VBox taoNoiDungBanVe() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label lblTitle = new Label("QUẢN LÝ BÁN VÉ");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0B3D91;");

        GridPane searchPane = new GridPane();
        searchPane.setHgap(10);
        searchPane.setVgap(10);
        searchPane.setPadding(new Insets(15));
        searchPane.setStyle("-fx-background-color: white; -fx-border-color: #D0D7DE;");

        txtGaDi = new TextField();
        txtGaDi.setPromptText("Ví dụ: Hà Nội");

        txtGaDen = new TextField();
        txtGaDen.setPromptText("Ví dụ: Đà Nẵng");

        Button btnTimVe = new Button("Tìm vé");
        btnTimVe.setOnAction(e -> timVe());

        Button btnLamMoi = new Button("Làm mới");
        btnLamMoi.setOnAction(e -> {
            txtGaDi.clear();
            txtGaDen.clear();
            tableVe.setItems(danhSachVe);
            txtHoaDon.setText("Đã hiển thị lại toàn bộ danh sách vé.");
        });

        searchPane.add(new Label("Ga đi:"), 0, 0);
        searchPane.add(txtGaDi, 1, 0);
        searchPane.add(new Label("Ga đến:"), 2, 0);
        searchPane.add(txtGaDen, 3, 0);
        searchPane.add(btnTimVe, 4, 0);
        searchPane.add(btnLamMoi, 5, 0);

        tableVe = taoBangVe();

        GridPane customerPane = new GridPane();
        customerPane.setHgap(10);
        customerPane.setVgap(10);
        customerPane.setPadding(new Insets(15));
        customerPane.setStyle("-fx-background-color: white; -fx-border-color: #D0D7DE;");

        txtTenKhach = new TextField();
        txtTenKhach.setPromptText("Nhập họ tên khách hàng");

        txtCCCD = new TextField();
        txtCCCD.setPromptText("Nhập CCCD hoặc SĐT");

        Button btnDatVe = new Button("Đặt vé");
        btnDatVe.setStyle("-fx-background-color: #008C45; -fx-text-fill: white; -fx-font-weight: bold;");
        btnDatVe.setOnAction(e -> datVe());

        customerPane.add(new Label("Tên khách hàng:"), 0, 0);
        customerPane.add(txtTenKhach, 1, 0);
        customerPane.add(new Label("CCCD/SĐT:"), 2, 0);
        customerPane.add(txtCCCD, 3, 0);
        customerPane.add(btnDatVe, 4, 0);

        txtHoaDon = new TextArea();
        txtHoaDon.setPromptText("Thông tin hóa đơn / thông báo sẽ hiển thị tại đây...");
        txtHoaDon.setPrefHeight(150);
        txtHoaDon.setEditable(false);

        content.getChildren().addAll(lblTitle, searchPane, tableVe, customerPane, txtHoaDon);

        return content;
    }

    private TableView<VeTau> taoBangVe() {
        TableView<VeTau> table = new TableView<>();
        table.setItems(danhSachVe);
        table.setPrefHeight(260);

        TableColumn<VeTau, String> colMaVe = new TableColumn<>("Mã vé");
        colMaVe.setCellValueFactory(new PropertyValueFactory<>("maVe"));
        colMaVe.setPrefWidth(100);

        TableColumn<VeTau, String> colGaDi = new TableColumn<>("Ga đi");
        colGaDi.setCellValueFactory(new PropertyValueFactory<>("gaDi"));
        colGaDi.setPrefWidth(130);

        TableColumn<VeTau, String> colGaDen = new TableColumn<>("Ga đến");
        colGaDen.setCellValueFactory(new PropertyValueFactory<>("gaDen"));
        colGaDen.setPrefWidth(130);

        TableColumn<VeTau, String> colGioDi = new TableColumn<>("Giờ đi");
        colGioDi.setCellValueFactory(new PropertyValueFactory<>("gioDi"));
        colGioDi.setPrefWidth(100);

        TableColumn<VeTau, Double> colGia = new TableColumn<>("Giá vé");
        colGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        colGia.setPrefWidth(120);

        TableColumn<VeTau, String> colTrangThai = new TableColumn<>("Trạng thái");
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colTrangThai.setPrefWidth(120);

        table.getColumns().addAll(colMaVe, colGaDi, colGaDen, colGioDi, colGia, colTrangThai);

        return table;
    }

    private VBox taoNoiDungQuanLyTau() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label lblTitle = new Label("QUẢN LÝ TÀU");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0B3D91;");

        Label lblMoTa = new Label("Demo nghiệp vụ: xem danh sách tàu, thêm tàu mới, tìm kiếm và cập nhật trạng thái khai thác.");
        lblMoTa.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555;");

        TableView<Tau> tableTau = new TableView<>();
        tableTau.setItems(danhSachTau);
        tableTau.setPrefHeight(260);

        TableColumn<Tau, String> colMaTau = new TableColumn<>("Mã tàu");
        colMaTau.setCellValueFactory(new PropertyValueFactory<>("maTau"));
        colMaTau.setPrefWidth(100);

        TableColumn<Tau, String> colTenTau = new TableColumn<>("Tên tàu");
        colTenTau.setCellValueFactory(new PropertyValueFactory<>("tenTau"));
        colTenTau.setPrefWidth(160);

        TableColumn<Tau, String> colLoaiTau = new TableColumn<>("Loại tàu");
        colLoaiTau.setCellValueFactory(new PropertyValueFactory<>("loaiTau"));
        colLoaiTau.setPrefWidth(140);

        TableColumn<Tau, String> colSoToa = new TableColumn<>("Số toa");
        colSoToa.setCellValueFactory(new PropertyValueFactory<>("soToa"));
        colSoToa.setPrefWidth(90);

        TableColumn<Tau, String> colTrangThai = new TableColumn<>("Trạng thái");
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colTrangThai.setPrefWidth(160);

        tableTau.getColumns().addAll(colMaTau, colTenTau, colLoaiTau, colSoToa, colTrangThai);

        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(15));
        formPane.setStyle("-fx-background-color: white; -fx-border-color: #D0D7DE;");

        TextField txtMaTau = new TextField();
        txtMaTau.setPromptText("VD: TAU04");

        TextField txtTenTau = new TextField();
        txtTenTau.setPromptText("VD: SE4");

        TextField txtLoaiTau = new TextField();
        txtLoaiTau.setPromptText("VD: Tàu khách");

        TextField txtSoToa = new TextField();
        txtSoToa.setPromptText("VD: 12");

        ComboBox<String> cbTrangThai = new ComboBox<>(FXCollections.observableArrayList(
                "Đang hoạt động", "Bảo trì", "Ngừng khai thác"));
        cbTrangThai.setValue("Đang hoạt động");
        cbTrangThai.setMaxWidth(Double.MAX_VALUE);

        TextField txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm theo mã, tên, loại, trạng thái...");

        TextArea txtThongBao = new TextArea();
        txtThongBao.setEditable(false);
        txtThongBao.setPrefHeight(90);
        txtThongBao.setText("Sẵn sàng quản lý danh sách tàu.");

        Button btnThem = new Button("Thêm tàu");
        btnThem.setStyle("-fx-background-color: #008C45; -fx-text-fill: white; -fx-font-weight: bold;");
        btnThem.setOnAction(e -> {
            String maTau = txtMaTau.getText().trim();
            String tenTau = txtTenTau.getText().trim();
            String loaiTau = txtLoaiTau.getText().trim();
            String soToa = txtSoToa.getText().trim();
            String trangThai = cbTrangThai.getValue();

            if (maTau.isEmpty() || tenTau.isEmpty() || loaiTau.isEmpty() || soToa.isEmpty()) {
                txtThongBao.setText("Vui lòng nhập đầy đủ mã tàu, tên tàu, loại tàu và số toa.");
                return;
            }

            for (Tau tau : danhSachTau) {
                if (tau.getMaTau().equalsIgnoreCase(maTau)) {
                    txtThongBao.setText("Mã tàu đã tồn tại. Vui lòng nhập mã khác.");
                    return;
                }
            }

            Tau tauMoi = new Tau(maTau, tenTau, loaiTau, soToa, trangThai);
            danhSachTau.add(tauMoi);
            tableTau.setItems(danhSachTau);
            txtThongBao.setText("Đã thêm tàu mới: " + maTau + " - " + tenTau + ".");

            txtMaTau.clear();
            txtTenTau.clear();
            txtLoaiTau.clear();
            txtSoToa.clear();
            cbTrangThai.setValue("Đang hoạt động");
        });

        Button btnCapNhat = new Button("Cập nhật trạng thái");
        btnCapNhat.setOnAction(e -> {
            Tau tauDangChon = tableTau.getSelectionModel().getSelectedItem();
            if (tauDangChon == null) {
                txtThongBao.setText("Vui lòng chọn một tàu cần cập nhật trạng thái.");
                return;
            }
            tauDangChon.setTrangThai(cbTrangThai.getValue());
            ObservableList<Tau> hienTai = tableTau.getItems();
            tableTau.setItems(null);
            tableTau.setItems(hienTai);
            txtThongBao.setText("Đã cập nhật trạng thái tàu " + tauDangChon.getMaTau()
                    + " thành: " + tauDangChon.getTrangThai() + ".");
        });

        Button btnTimKiem = new Button("Tìm kiếm");
        btnTimKiem.setOnAction(e -> {
            String tuKhoa = txtTimKiem.getText().trim().toLowerCase();
            ObservableList<Tau> ketQua = FXCollections.observableArrayList();

            for (Tau tau : danhSachTau) {
                boolean phuHop = tuKhoa.isEmpty()
                        || tau.getMaTau().toLowerCase().contains(tuKhoa)
                        || tau.getTenTau().toLowerCase().contains(tuKhoa)
                        || tau.getLoaiTau().toLowerCase().contains(tuKhoa)
                        || tau.getTrangThai().toLowerCase().contains(tuKhoa);
                if (phuHop) {
                    ketQua.add(tau);
                }
            }

            tableTau.setItems(ketQua);
            txtThongBao.setText("Đã tìm thấy " + ketQua.size() + " tàu phù hợp.");
        });

        Button btnLamMoi = new Button("Làm mới");
        btnLamMoi.setOnAction(e -> {
            txtTimKiem.clear();
            tableTau.setItems(danhSachTau);
            txtThongBao.setText("Đã hiển thị lại toàn bộ danh sách tàu.");
        });

        formPane.add(new Label("Mã tàu:"), 0, 0);
        formPane.add(txtMaTau, 1, 0);
        formPane.add(new Label("Tên tàu:"), 2, 0);
        formPane.add(txtTenTau, 3, 0);
        formPane.add(new Label("Loại tàu:"), 0, 1);
        formPane.add(txtLoaiTau, 1, 1);
        formPane.add(new Label("Số toa:"), 2, 1);
        formPane.add(txtSoToa, 3, 1);
        formPane.add(new Label("Trạng thái:"), 0, 2);
        formPane.add(cbTrangThai, 1, 2);
        formPane.add(btnThem, 2, 2);
        formPane.add(btnCapNhat, 3, 2);
        formPane.add(new Label("Tìm kiếm:"), 0, 3);
        formPane.add(txtTimKiem, 1, 3, 2, 1);
        formPane.add(btnTimKiem, 3, 3);
        formPane.add(btnLamMoi, 4, 3);

        content.getChildren().addAll(lblTitle, lblMoTa, tableTau, formPane, txtThongBao);
        return content;
    }

    private VBox taoNoiDungQuanLyLichTrinh() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label lblTitle = new Label("QUẢN LÝ LỊCH TRÌNH");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0B3D91;");

        Label lblMoTa = new Label("Demo nghiệp vụ: tra cứu lịch trình, thêm lịch trình mới và cập nhật trạng thái chạy tàu.");
        lblMoTa.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555;");

        TableView<LichTrinh> tableLichTrinh = new TableView<>();
        tableLichTrinh.setItems(danhSachLichTrinh);
        tableLichTrinh.setPrefHeight(260);

        TableColumn<LichTrinh, String> colMaLT = new TableColumn<>("Mã lịch trình");
        colMaLT.setCellValueFactory(new PropertyValueFactory<>("maLichTrinh"));
        colMaLT.setPrefWidth(120);

        TableColumn<LichTrinh, String> colMaTau = new TableColumn<>("Mã tàu");
        colMaTau.setCellValueFactory(new PropertyValueFactory<>("maTau"));
        colMaTau.setPrefWidth(90);

        TableColumn<LichTrinh, String> colGaDi = new TableColumn<>("Ga đi");
        colGaDi.setCellValueFactory(new PropertyValueFactory<>("gaDi"));
        colGaDi.setPrefWidth(120);

        TableColumn<LichTrinh, String> colGaDen = new TableColumn<>("Ga đến");
        colGaDen.setCellValueFactory(new PropertyValueFactory<>("gaDen"));
        colGaDen.setPrefWidth(120);

        TableColumn<LichTrinh, String> colGioDi = new TableColumn<>("Giờ đi");
        colGioDi.setCellValueFactory(new PropertyValueFactory<>("gioDi"));
        colGioDi.setPrefWidth(90);

        TableColumn<LichTrinh, String> colGioDen = new TableColumn<>("Giờ đến");
        colGioDen.setCellValueFactory(new PropertyValueFactory<>("gioDen"));
        colGioDen.setPrefWidth(90);

        TableColumn<LichTrinh, String> colTrangThai = new TableColumn<>("Trạng thái");
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colTrangThai.setPrefWidth(140);

        tableLichTrinh.getColumns().addAll(colMaLT, colMaTau, colGaDi, colGaDen, colGioDi, colGioDen, colTrangThai);

        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(15));
        formPane.setStyle("-fx-background-color: white; -fx-border-color: #D0D7DE;");

        TextField txtMaLT = new TextField();
        txtMaLT.setPromptText("VD: LT004");

        ComboBox<String> cbMaTau = new ComboBox<>();
        for (Tau tau : danhSachTau) {
            cbMaTau.getItems().add(tau.getMaTau());
        }
        if (!cbMaTau.getItems().isEmpty()) {
            cbMaTau.setValue(cbMaTau.getItems().get(0));
        }
        cbMaTau.setMaxWidth(Double.MAX_VALUE);

        TextField txtGaDiForm = new TextField();
        txtGaDiForm.setPromptText("VD: Hà Nội");

        TextField txtGaDenForm = new TextField();
        txtGaDenForm.setPromptText("VD: Đà Nẵng");

        TextField txtGioDi = new TextField();
        txtGioDi.setPromptText("VD: 07:00");

        TextField txtGioDen = new TextField();
        txtGioDen.setPromptText("VD: 17:30");

        ComboBox<String> cbTrangThai = new ComboBox<>(FXCollections.observableArrayList(
                "Đang mở bán", "Đã khóa", "Tạm hoãn", "Hoàn thành"));
        cbTrangThai.setValue("Đang mở bán");
        cbTrangThai.setMaxWidth(Double.MAX_VALUE);

        TextField txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm theo ga, mã tàu, trạng thái...");

        TextArea txtThongBao = new TextArea();
        txtThongBao.setEditable(false);
        txtThongBao.setPrefHeight(90);
        txtThongBao.setText("Sẵn sàng quản lý lịch trình tàu chạy.");

        Button btnThem = new Button("Thêm lịch trình");
        btnThem.setStyle("-fx-background-color: #008C45; -fx-text-fill: white; -fx-font-weight: bold;");
        btnThem.setOnAction(e -> {
            String maLT = txtMaLT.getText().trim();
            String maTau = cbMaTau.getValue();
            String gaDi = txtGaDiForm.getText().trim();
            String gaDen = txtGaDenForm.getText().trim();
            String gioDi = txtGioDi.getText().trim();
            String gioDen = txtGioDen.getText().trim();
            String trangThai = cbTrangThai.getValue();

            if (maLT.isEmpty() || maTau == null || gaDi.isEmpty() || gaDen.isEmpty() || gioDi.isEmpty() || gioDen.isEmpty()) {
                txtThongBao.setText("Vui lòng nhập đầy đủ mã lịch trình, mã tàu, ga đi, ga đến, giờ đi và giờ đến.");
                return;
            }

            if (gaDi.equalsIgnoreCase(gaDen)) {
                txtThongBao.setText("Ga đi và ga đến không được trùng nhau.");
                return;
            }

            for (LichTrinh lt : danhSachLichTrinh) {
                if (lt.getMaLichTrinh().equalsIgnoreCase(maLT)) {
                    txtThongBao.setText("Mã lịch trình đã tồn tại. Vui lòng nhập mã khác.");
                    return;
                }
                if (lt.getMaTau().equalsIgnoreCase(maTau) && lt.getGioDi().equalsIgnoreCase(gioDi)) {
                    txtThongBao.setText("Tàu " + maTau + " đã có lịch trình khác tại giờ " + gioDi + ".");
                    return;
                }
            }

            LichTrinh lichTrinhMoi = new LichTrinh(maLT, maTau, gaDi, gaDen, gioDi, gioDen, trangThai);
            danhSachLichTrinh.add(lichTrinhMoi);
            tableLichTrinh.setItems(danhSachLichTrinh);
            txtThongBao.setText("Đã thêm lịch trình mới: " + maLT + " cho tàu " + maTau + ".");

            txtMaLT.clear();
            txtGaDiForm.clear();
            txtGaDenForm.clear();
            txtGioDi.clear();
            txtGioDen.clear();
            cbTrangThai.setValue("Đang mở bán");
        });

        Button btnCapNhat = new Button("Cập nhật trạng thái");
        btnCapNhat.setOnAction(e -> {
            LichTrinh ltDangChon = tableLichTrinh.getSelectionModel().getSelectedItem();
            if (ltDangChon == null) {
                txtThongBao.setText("Vui lòng chọn một lịch trình cần cập nhật trạng thái.");
                return;
            }
            ltDangChon.setTrangThai(cbTrangThai.getValue());
            ObservableList<LichTrinh> hienTai = tableLichTrinh.getItems();
            tableLichTrinh.setItems(null);
            tableLichTrinh.setItems(hienTai);
            txtThongBao.setText("Đã cập nhật trạng thái lịch trình " + ltDangChon.getMaLichTrinh()
                    + " thành: " + ltDangChon.getTrangThai() + ".");
        });

        Button btnTimKiem = new Button("Tìm kiếm");
        btnTimKiem.setOnAction(e -> {
            String tuKhoa = txtTimKiem.getText().trim().toLowerCase();
            ObservableList<LichTrinh> ketQua = FXCollections.observableArrayList();

            for (LichTrinh lt : danhSachLichTrinh) {
                boolean phuHop = tuKhoa.isEmpty()
                        || lt.getMaLichTrinh().toLowerCase().contains(tuKhoa)
                        || lt.getMaTau().toLowerCase().contains(tuKhoa)
                        || lt.getGaDi().toLowerCase().contains(tuKhoa)
                        || lt.getGaDen().toLowerCase().contains(tuKhoa)
                        || lt.getTrangThai().toLowerCase().contains(tuKhoa);
                if (phuHop) {
                    ketQua.add(lt);
                }
            }

            tableLichTrinh.setItems(ketQua);
            txtThongBao.setText("Đã tìm thấy " + ketQua.size() + " lịch trình phù hợp.");
        });

        Button btnLamMoi = new Button("Làm mới");
        btnLamMoi.setOnAction(e -> {
            txtTimKiem.clear();
            tableLichTrinh.setItems(danhSachLichTrinh);
            txtThongBao.setText("Đã hiển thị lại toàn bộ danh sách lịch trình.");
        });

        formPane.add(new Label("Mã lịch trình:"), 0, 0);
        formPane.add(txtMaLT, 1, 0);
        formPane.add(new Label("Mã tàu:"), 2, 0);
        formPane.add(cbMaTau, 3, 0);
        formPane.add(new Label("Ga đi:"), 0, 1);
        formPane.add(txtGaDiForm, 1, 1);
        formPane.add(new Label("Ga đến:"), 2, 1);
        formPane.add(txtGaDenForm, 3, 1);
        formPane.add(new Label("Giờ đi:"), 0, 2);
        formPane.add(txtGioDi, 1, 2);
        formPane.add(new Label("Giờ đến:"), 2, 2);
        formPane.add(txtGioDen, 3, 2);
        formPane.add(new Label("Trạng thái:"), 0, 3);
        formPane.add(cbTrangThai, 1, 3);
        formPane.add(btnThem, 2, 3);
        formPane.add(btnCapNhat, 3, 3);
        formPane.add(new Label("Tìm kiếm:"), 0, 4);
        formPane.add(txtTimKiem, 1, 4, 2, 1);
        formPane.add(btnTimKiem, 3, 4);
        formPane.add(btnLamMoi, 4, 4);

        content.getChildren().addAll(lblTitle, lblMoTa, tableLichTrinh, formPane, txtThongBao);
        return content;
    }

    private void taoDuLieuMau() {
        danhSachVe.add(new VeTau("VE001", "Hà Nội", "Đà Nẵng", "07:00", 500000, "Còn trống"));
        danhSachVe.add(new VeTau("VE002", "Hà Nội", "Lào Cai", "08:30", 250000, "Còn trống"));
        danhSachVe.add(new VeTau("VE003", "Đà Nẵng", "TP.HCM", "09:00", 650000, "Đã đặt"));
        danhSachVe.add(new VeTau("VE004", "Hà Nội", "Huế", "10:15", 420000, "Còn trống"));
        danhSachVe.add(new VeTau("VE005", "Lào Cai", "Hà Nội", "14:00", 260000, "Còn trống"));
        danhSachVe.add(new VeTau("VE006", "Hà Nội", "Vinh", "16:30", 320000, "Còn trống"));

        danhSachTau.add(new Tau("TAU01", "SE1", "Tàu khách Bắc - Nam", "12", "Đang hoạt động"));
        danhSachTau.add(new Tau("TAU02", "SE3", "Tàu khách nhanh", "10", "Đang hoạt động"));
        danhSachTau.add(new Tau("TAU03", "LC5", "Tàu tuyến Hà Nội - Lào Cai", "8", "Bảo trì"));

        danhSachLichTrinh.add(new LichTrinh("LT001", "TAU01", "Hà Nội", "Đà Nẵng", "07:00", "17:30", "Đang mở bán"));
        danhSachLichTrinh.add(new LichTrinh("LT002", "TAU02", "Hà Nội", "Lào Cai", "08:30", "14:00", "Đang mở bán"));
        danhSachLichTrinh.add(new LichTrinh("LT003", "TAU03", "Đà Nẵng", "TP.HCM", "09:00", "23:00", "Tạm hoãn"));
    }

    private void timVe() {
        String gaDi = txtGaDi.getText().trim().toLowerCase();
        String gaDen = txtGaDen.getText().trim().toLowerCase();

        ObservableList<VeTau> ketQua = FXCollections.observableArrayList();

        for (VeTau ve : danhSachVe) {
            boolean dungGaDi = gaDi.isEmpty() || ve.getGaDi().toLowerCase().contains(gaDi);
            boolean dungGaDen = gaDen.isEmpty() || ve.getGaDen().toLowerCase().contains(gaDen);

            if (dungGaDi && dungGaDen) {
                ketQua.add(ve);
            }
        }

        tableVe.setItems(ketQua);
        txtHoaDon.setText("Đã tìm thấy " + ketQua.size() + " vé phù hợp.");
    }

    private void datVe() {
        VeTau veDangChon = tableVe.getSelectionModel().getSelectedItem();

        if (veDangChon == null) {
            txtHoaDon.setText("Vui lòng chọn một vé cần đặt.");
            return;
        }

        if ("Đã đặt".equalsIgnoreCase(veDangChon.getTrangThai())) {
            txtHoaDon.setText("Vé này đã được đặt. Vui lòng chọn vé khác.");
            return;
        }

        String tenKhach = txtTenKhach.getText().trim();
        String cccd = txtCCCD.getText().trim();

        if (tenKhach.isEmpty() || cccd.isEmpty()) {
            txtHoaDon.setText("Vui lòng nhập đầy đủ tên khách hàng và CCCD/SĐT.");
            return;
        }

        veDangChon.setTrangThai("Đã đặt");

        ObservableList<VeTau> hienTai = tableVe.getItems();
        tableVe.setItems(null);
        tableVe.setItems(hienTai);

        DecimalFormat df = new DecimalFormat("#,###");

        String hoaDon =
                "ĐẶT VÉ THÀNH CÔNG\n"
                + "----------------------------------\n"
                + "Mã vé: " + veDangChon.getMaVe() + "\n"
                + "Khách hàng: " + tenKhach + "\n"
                + "CCCD/SĐT: " + cccd + "\n"
                + "Ga đi: " + veDangChon.getGaDi() + "\n"
                + "Ga đến: " + veDangChon.getGaDen() + "\n"
                + "Giờ đi: " + veDangChon.getGioDi() + "\n"
                + "Giá vé: " + df.format(veDangChon.getGia()) + " VNĐ\n"
                + "Trạng thái vé: " + veDangChon.getTrangThai() + "\n"
                + "----------------------------------\n"
                + "Hệ thống đã lưu thông tin đặt vé và tạo hóa đơn.";

        txtHoaDon.setText(hoaDon);
    }

    private void thongBaoChucNangDangMoPhong(String tenChucNang) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(tenChucNang);
        alert.setContentText("Chức năng này đang được mô phỏng trong phạm vi demo. Nhóm tập trung demo luồng chính của phân hệ bán vé.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class VeTau {
        private final SimpleStringProperty maVe;
        private final SimpleStringProperty gaDi;
        private final SimpleStringProperty gaDen;
        private final SimpleStringProperty gioDi;
        private final SimpleDoubleProperty gia;
        private final SimpleStringProperty trangThai;

        public VeTau(String maVe, String gaDi, String gaDen, String gioDi, double gia, String trangThai) {
            this.maVe = new SimpleStringProperty(maVe);
            this.gaDi = new SimpleStringProperty(gaDi);
            this.gaDen = new SimpleStringProperty(gaDen);
            this.gioDi = new SimpleStringProperty(gioDi);
            this.gia = new SimpleDoubleProperty(gia);
            this.trangThai = new SimpleStringProperty(trangThai);
        }

        public String getMaVe() {
            return maVe.get();
        }

        public String getGaDi() {
            return gaDi.get();
        }

        public String getGaDen() {
            return gaDen.get();
        }

        public String getGioDi() {
            return gioDi.get();
        }

        public double getGia() {
            return gia.get();
        }

        public String getTrangThai() {
            return trangThai.get();
        }

        public void setTrangThai(String trangThai) {
            this.trangThai.set(trangThai);
        }
    }

    public static class Tau {
        private final SimpleStringProperty maTau;
        private final SimpleStringProperty tenTau;
        private final SimpleStringProperty loaiTau;
        private final SimpleStringProperty soToa;
        private final SimpleStringProperty trangThai;

        public Tau(String maTau, String tenTau, String loaiTau, String soToa, String trangThai) {
            this.maTau = new SimpleStringProperty(maTau);
            this.tenTau = new SimpleStringProperty(tenTau);
            this.loaiTau = new SimpleStringProperty(loaiTau);
            this.soToa = new SimpleStringProperty(soToa);
            this.trangThai = new SimpleStringProperty(trangThai);
        }

        public String getMaTau() {
            return maTau.get();
        }

        public String getTenTau() {
            return tenTau.get();
        }

        public String getLoaiTau() {
            return loaiTau.get();
        }

        public String getSoToa() {
            return soToa.get();
        }

        public String getTrangThai() {
            return trangThai.get();
        }

        public void setTrangThai(String trangThai) {
            this.trangThai.set(trangThai);
        }
    }

    public static class LichTrinh {
        private final SimpleStringProperty maLichTrinh;
        private final SimpleStringProperty maTau;
        private final SimpleStringProperty gaDi;
        private final SimpleStringProperty gaDen;
        private final SimpleStringProperty gioDi;
        private final SimpleStringProperty gioDen;
        private final SimpleStringProperty trangThai;

        public LichTrinh(String maLichTrinh, String maTau, String gaDi, String gaDen,
                String gioDi, String gioDen, String trangThai) {
            this.maLichTrinh = new SimpleStringProperty(maLichTrinh);
            this.maTau = new SimpleStringProperty(maTau);
            this.gaDi = new SimpleStringProperty(gaDi);
            this.gaDen = new SimpleStringProperty(gaDen);
            this.gioDi = new SimpleStringProperty(gioDi);
            this.gioDen = new SimpleStringProperty(gioDen);
            this.trangThai = new SimpleStringProperty(trangThai);
        }

        public String getMaLichTrinh() {
            return maLichTrinh.get();
        }

        public String getMaTau() {
            return maTau.get();
        }

        public String getGaDi() {
            return gaDi.get();
        }

        public String getGaDen() {
            return gaDen.get();
        }

        public String getGioDi() {
            return gioDi.get();
        }

        public String getGioDen() {
            return gioDen.get();
        }

        public String getTrangThai() {
            return trangThai.get();
        }

        public void setTrangThai(String trangThai) {
            this.trangThai.set(trangThai);
        }
    }
}