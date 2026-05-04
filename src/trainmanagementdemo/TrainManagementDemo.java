package trainmanagementdemo;

import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        Label lblTitle = new Label("TrainManagement - Quản lý bán vé");
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

        btnBanVe.setOnAction(e -> {
            BorderPane root = (BorderPane) primaryStage.getScene().getRoot();
            root.setCenter(taoNoiDungBanVe());
        });

        btnTau.setOnAction(e -> thongBaoChucNangDangMoPhong("Quản lý tàu"));
        btnLichTrinh.setOnAction(e -> thongBaoChucNangDangMoPhong("Quản lý lịch trình"));
        btnNhanVien.setOnAction(e -> thongBaoChucNangDangMoPhong("Quản lý nhân viên"));
        btnThuChi.setOnAction(e -> thongBaoChucNangDangMoPhong("Thống kê thu chi"));

        menu.getChildren().addAll(lblMenu, btnBanVe, btnTau, btnLichTrinh, btnNhanVien, btnThuChi);

        return menu;
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

    private void taoDuLieuMau() {
        danhSachVe.add(new VeTau("VE001", "Hà Nội", "Đà Nẵng", "07:00", 500000, "Còn trống"));
        danhSachVe.add(new VeTau("VE002", "Hà Nội", "Lào Cai", "08:30", 250000, "Còn trống"));
        danhSachVe.add(new VeTau("VE003", "Đà Nẵng", "TP.HCM", "09:00", 650000, "Đã đặt"));
        danhSachVe.add(new VeTau("VE004", "Hà Nội", "Huế", "10:15", 420000, "Còn trống"));
        danhSachVe.add(new VeTau("VE005", "Lào Cai", "Hà Nội", "14:00", 260000, "Còn trống"));
        danhSachVe.add(new VeTau("VE006", "Hà Nội", "Vinh", "16:30", 320000, "Còn trống"));
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
}