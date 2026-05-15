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

    private final ObservableList<VeTau> danhSachVe = FXCollections.observableArrayList();
    private final ObservableList<Tau> danhSachTau = FXCollections.observableArrayList();
    private final ObservableList<LichTrinh> danhSachLichTrinh = FXCollections.observableArrayList();
    private final ObservableList<NhanVien> danhSachNhanVien = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        taoDuLieuMau();

        primaryStage.setTitle("Hệ thống quản lý đường sắt thông minh");
        primaryStage.setScene(taoManHinhChao());
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
        btnNhanVien.setOnAction(e -> doiNoiDung(taoNoiDungQuanLyNhanVien()));
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

    private Label taoTieuDe(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0B3D91;");
        return lbl;
    }

    private GridPane taoKhungNhap() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(15));
        pane.setStyle("-fx-background-color: white; -fx-border-color: #D0D7DE;");
        return pane;
    }

    private <S, T> TableColumn<S, T> taoCot(String title, String property, int width) {
        TableColumn<S, T> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setPrefWidth(width);
        return col;
    }

    private TextArea taoVungThongBao(String text) {
        TextArea area = new TextArea(text);
        area.setEditable(false);
        area.setPrefHeight(90);
        return area;
    }

    private VBox taoNoiDungBanVe() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        GridPane searchPane = taoKhungNhap();
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

        GridPane customerPane = taoKhungNhap();
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

        content.getChildren().addAll(taoTieuDe("QUẢN LÝ BÁN VÉ"), searchPane, tableVe, customerPane, txtHoaDon);
        return content;
    }

    private TableView<VeTau> taoBangVe() {
        TableView<VeTau> table = new TableView<>();
        table.setItems(danhSachVe);
        table.setPrefHeight(260);
        table.getColumns().addAll(
                taoCot("Mã vé", "maVe", 100),
                taoCot("Ga đi", "gaDi", 130),
                taoCot("Ga đến", "gaDen", 130),
                taoCot("Giờ đi", "gioDi", 100),
                taoCot("Giá vé", "gia", 120),
                taoCot("Trạng thái", "trangThai", 120));
        return table;
    }

    private VBox taoNoiDungQuanLyTau() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label lblMoTa = new Label("Demo nghiệp vụ: xem danh sách tàu, thêm tàu mới, tìm kiếm và cập nhật trạng thái khai thác.");
        lblMoTa.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555;");

        TableView<Tau> tableTau = new TableView<>();
        tableTau.setItems(danhSachTau);
        tableTau.setPrefHeight(260);
        tableTau.getColumns().addAll(
                taoCot("Mã tàu", "maTau", 100),
                taoCot("Tên tàu", "tenTau", 160),
                taoCot("Loại tàu", "loaiTau", 140),
                taoCot("Số toa", "soToa", 90),
                taoCot("Trạng thái", "trangThai", 160));

        GridPane formPane = taoKhungNhap();
        TextField txtMaTau = new TextField();
        txtMaTau.setPromptText("VD: TAU04");
        TextField txtTenTau = new TextField();
        txtTenTau.setPromptText("VD: SE4");
        TextField txtLoaiTau = new TextField();
        txtLoaiTau.setPromptText("VD: Tàu khách");
        TextField txtSoToa = new TextField();
        txtSoToa.setPromptText("VD: 12");
        ComboBox<String> cbTrangThai = new ComboBox<>(FXCollections.observableArrayList("Đang hoạt động", "Bảo trì", "Ngừng khai thác"));
        cbTrangThai.setValue("Đang hoạt động");
        cbTrangThai.setMaxWidth(Double.MAX_VALUE);
        TextField txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm theo mã, tên, loại, trạng thái...");
        TextArea txtThongBao = taoVungThongBao("Sẵn sàng quản lý danh sách tàu.");

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
            danhSachTau.add(new Tau(maTau, tenTau, loaiTau, soToa, trangThai));
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
            lamMoiBang(tableTau, tableTau.getItems());
            txtThongBao.setText("Đã cập nhật trạng thái tàu " + tauDangChon.getMaTau() + " thành: " + tauDangChon.getTrangThai() + ".");
        });

        Button btnTimKiem = new Button("Tìm kiếm");
        btnTimKiem.setOnAction(e -> {
            String tuKhoa = txtTimKiem.getText().trim().toLowerCase();
            ObservableList<Tau> ketQua = FXCollections.observableArrayList();
            for (Tau tau : danhSachTau) {
                if (tuKhoa.isEmpty()
                        || tau.getMaTau().toLowerCase().contains(tuKhoa)
                        || tau.getTenTau().toLowerCase().contains(tuKhoa)
                        || tau.getLoaiTau().toLowerCase().contains(tuKhoa)
                        || tau.getTrangThai().toLowerCase().contains(tuKhoa)) {
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

        content.getChildren().addAll(taoTieuDe("QUẢN LÝ TÀU"), lblMoTa, tableTau, formPane, txtThongBao);
        return content;
    }

    private VBox taoNoiDungQuanLyLichTrinh() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label lblMoTa = new Label("Demo nghiệp vụ: tra cứu lịch trình, thêm lịch trình mới và cập nhật trạng thái chạy tàu.");
        lblMoTa.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555;");

        TableView<LichTrinh> tableLichTrinh = new TableView<>();
        tableLichTrinh.setItems(danhSachLichTrinh);
        tableLichTrinh.setPrefHeight(260);
        tableLichTrinh.getColumns().addAll(
                taoCot("Mã lịch trình", "maLichTrinh", 120),
                taoCot("Mã tàu", "maTau", 90),
                taoCot("Ga đi", "gaDi", 120),
                taoCot("Ga đến", "gaDen", 120),
                taoCot("Giờ đi", "gioDi", 90),
                taoCot("Giờ đến", "gioDen", 90),
                taoCot("Trạng thái", "trangThai", 140));

        GridPane formPane = taoKhungNhap();
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
        ComboBox<String> cbTrangThai = new ComboBox<>(FXCollections.observableArrayList("Đang mở bán", "Đã khóa", "Tạm hoãn", "Hoàn thành"));
        cbTrangThai.setValue("Đang mở bán");
        cbTrangThai.setMaxWidth(Double.MAX_VALUE);
        TextField txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm theo ga, mã tàu, trạng thái...");
        TextArea txtThongBao = taoVungThongBao("Sẵn sàng quản lý lịch trình tàu chạy.");

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
            danhSachLichTrinh.add(new LichTrinh(maLT, maTau, gaDi, gaDen, gioDi, gioDen, trangThai));
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
            lamMoiBang(tableLichTrinh, tableLichTrinh.getItems());
            txtThongBao.setText("Đã cập nhật trạng thái lịch trình " + ltDangChon.getMaLichTrinh() + " thành: " + ltDangChon.getTrangThai() + ".");
        });

        Button btnTimKiem = new Button("Tìm kiếm");
        btnTimKiem.setOnAction(e -> {
            String tuKhoa = txtTimKiem.getText().trim().toLowerCase();
            ObservableList<LichTrinh> ketQua = FXCollections.observableArrayList();
            for (LichTrinh lt : danhSachLichTrinh) {
                if (tuKhoa.isEmpty()
                        || lt.getMaLichTrinh().toLowerCase().contains(tuKhoa)
                        || lt.getMaTau().toLowerCase().contains(tuKhoa)
                        || lt.getGaDi().toLowerCase().contains(tuKhoa)
                        || lt.getGaDen().toLowerCase().contains(tuKhoa)
                        || lt.getTrangThai().toLowerCase().contains(tuKhoa)) {
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

        content.getChildren().addAll(taoTieuDe("QUẢN LÝ LỊCH TRÌNH"), lblMoTa, tableLichTrinh, formPane, txtThongBao);
        return content;
    }

    private VBox taoNoiDungQuanLyNhanVien() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label lblMoTa = new Label("Demo nghiệp vụ: xem danh sách nhân viên, thêm hồ sơ, tìm kiếm và khóa/ngừng hoạt động nhân viên.");
        lblMoTa.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555;");

        TableView<NhanVien> tableNhanVien = new TableView<>();
        tableNhanVien.setItems(danhSachNhanVien);
        tableNhanVien.setPrefHeight(260);
        tableNhanVien.getColumns().addAll(
                taoCot("Mã NV", "maNV", 90),
                taoCot("Họ tên", "hoTen", 160),
                taoCot("CCCD", "cccd", 120),
                taoCot("SĐT", "sdt", 120),
                taoCot("Bộ phận", "boPhan", 130),
                taoCot("Ca làm", "caLam", 90),
                taoCot("Trạng thái", "trangThai", 130));

        GridPane formPane = taoKhungNhap();
        TextField txtMaNV = new TextField();
        txtMaNV.setPromptText("VD: NV004");
        TextField txtHoTen = new TextField();
        txtHoTen.setPromptText("VD: Nguyễn Văn A");
        TextField txtCCCDNV = new TextField();
        txtCCCDNV.setPromptText("VD: 001203000111");
        TextField txtSDT = new TextField();
        txtSDT.setPromptText("VD: 0912345678");
        ComboBox<String> cbBoPhan = new ComboBox<>(FXCollections.observableArrayList("Bán vé", "Điều hành", "Kế toán", "Kỹ thuật", "Dịch vụ"));
        cbBoPhan.setValue("Bán vé");
        cbBoPhan.setMaxWidth(Double.MAX_VALUE);
        ComboBox<String> cbCaLam = new ComboBox<>(FXCollections.observableArrayList("Sáng", "Chiều", "Tối", "Hành chính"));
        cbCaLam.setValue("Sáng");
        cbCaLam.setMaxWidth(Double.MAX_VALUE);
        ComboBox<String> cbTrangThai = new ComboBox<>(FXCollections.observableArrayList("Đang làm", "Tạm nghỉ", "Đã khóa"));
        cbTrangThai.setValue("Đang làm");
        cbTrangThai.setMaxWidth(Double.MAX_VALUE);
        TextField txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm theo mã, tên, bộ phận, trạng thái...");
        TextArea txtThongBao = taoVungThongBao("Sẵn sàng quản lý hồ sơ nhân viên.");

        Button btnThem = new Button("Thêm nhân viên");
        btnThem.setStyle("-fx-background-color: #008C45; -fx-text-fill: white; -fx-font-weight: bold;");
        btnThem.setOnAction(e -> {
            String maNV = txtMaNV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String cccd = txtCCCDNV.getText().trim();
            String sdt = txtSDT.getText().trim();
            String boPhan = cbBoPhan.getValue();
            String caLam = cbCaLam.getValue();
            String trangThai = cbTrangThai.getValue();

            if (maNV.isEmpty() || hoTen.isEmpty() || cccd.isEmpty() || sdt.isEmpty()) {
                txtThongBao.setText("Vui lòng nhập đầy đủ mã nhân viên, họ tên, CCCD và số điện thoại.");
                return;
            }
            for (NhanVien nv : danhSachNhanVien) {
                if (nv.getMaNV().equalsIgnoreCase(maNV)) {
                    txtThongBao.setText("Mã nhân viên đã tồn tại. Vui lòng nhập mã khác.");
                    return;
                }
                if (nv.getCCCD().equalsIgnoreCase(cccd)) {
                    txtThongBao.setText("CCCD đã tồn tại trong hệ thống. Vui lòng kiểm tra lại.");
                    return;
                }
            }
            danhSachNhanVien.add(new NhanVien(maNV, hoTen, cccd, sdt, boPhan, caLam, trangThai));
            tableNhanVien.setItems(danhSachNhanVien);
            txtThongBao.setText("Đã thêm nhân viên mới: " + maNV + " - " + hoTen + ".");
            txtMaNV.clear();
            txtHoTen.clear();
            txtCCCDNV.clear();
            txtSDT.clear();
            cbBoPhan.setValue("Bán vé");
            cbCaLam.setValue("Sáng");
            cbTrangThai.setValue("Đang làm");
        });

        Button btnCapNhat = new Button("Cập nhật trạng thái");
        btnCapNhat.setOnAction(e -> {
            NhanVien nvDangChon = tableNhanVien.getSelectionModel().getSelectedItem();
            if (nvDangChon == null) {
                txtThongBao.setText("Vui lòng chọn một nhân viên cần cập nhật trạng thái.");
                return;
            }
            nvDangChon.setTrangThai(cbTrangThai.getValue());
            lamMoiBang(tableNhanVien, tableNhanVien.getItems());
            txtThongBao.setText("Đã cập nhật trạng thái nhân viên " + nvDangChon.getMaNV() + " thành: " + nvDangChon.getTrangThai() + ".");
        });

        Button btnTimKiem = new Button("Tìm kiếm");
        btnTimKiem.setOnAction(e -> {
            String tuKhoa = txtTimKiem.getText().trim().toLowerCase();
            ObservableList<NhanVien> ketQua = FXCollections.observableArrayList();
            for (NhanVien nv : danhSachNhanVien) {
                if (tuKhoa.isEmpty()
                        || nv.getMaNV().toLowerCase().contains(tuKhoa)
                        || nv.getHoTen().toLowerCase().contains(tuKhoa)
                        || nv.getBoPhan().toLowerCase().contains(tuKhoa)
                        || nv.getCaLam().toLowerCase().contains(tuKhoa)
                        || nv.getTrangThai().toLowerCase().contains(tuKhoa)) {
                    ketQua.add(nv);
                }
            }
            tableNhanVien.setItems(ketQua);
            txtThongBao.setText("Đã tìm thấy " + ketQua.size() + " nhân viên phù hợp.");
        });

        Button btnLamMoi = new Button("Làm mới");
        btnLamMoi.setOnAction(e -> {
            txtTimKiem.clear();
            tableNhanVien.setItems(danhSachNhanVien);
            txtThongBao.setText("Đã hiển thị lại toàn bộ danh sách nhân viên.");
        });

        formPane.add(new Label("Mã NV:"), 0, 0);
        formPane.add(txtMaNV, 1, 0);
        formPane.add(new Label("Họ tên:"), 2, 0);
        formPane.add(txtHoTen, 3, 0);
        formPane.add(new Label("CCCD:"), 0, 1);
        formPane.add(txtCCCDNV, 1, 1);
        formPane.add(new Label("SĐT:"), 2, 1);
        formPane.add(txtSDT, 3, 1);
        formPane.add(new Label("Bộ phận:"), 0, 2);
        formPane.add(cbBoPhan, 1, 2);
        formPane.add(new Label("Ca làm:"), 2, 2);
        formPane.add(cbCaLam, 3, 2);
        formPane.add(new Label("Trạng thái:"), 0, 3);
        formPane.add(cbTrangThai, 1, 3);
        formPane.add(btnThem, 2, 3);
        formPane.add(btnCapNhat, 3, 3);
        formPane.add(new Label("Tìm kiếm:"), 0, 4);
        formPane.add(txtTimKiem, 1, 4, 2, 1);
        formPane.add(btnTimKiem, 3, 4);
        formPane.add(btnLamMoi, 4, 4);

        content.getChildren().addAll(taoTieuDe("QUẢN LÝ NHÂN VIÊN"), lblMoTa, tableNhanVien, formPane, txtThongBao);
        return content;
    }

    private <T> void lamMoiBang(TableView<T> table, ObservableList<T> items) {
        table.setItems(null);
        table.setItems(items);
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

        danhSachNhanVien.add(new NhanVien("NV001", "Nguyễn Việt Long", "001203000001", "0901000001", "Bán vé", "Sáng", "Đang làm"));
        danhSachNhanVien.add(new NhanVien("NV002", "Trần Nam Phong", "001203000002", "0901000002", "Điều hành", "Hành chính", "Đang làm"));
        danhSachNhanVien.add(new NhanVien("NV003", "Nguyễn Tấn Dũng", "001203000003", "0901000003", "Kế toán", "Chiều", "Tạm nghỉ"));
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
        lamMoiBang(tableVe, tableVe.getItems());
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
        alert.setContentText("Chức năng này đang được mô phỏng trong phạm vi demo. Nhóm tập trung demo các luồng chính trước, dữ liệu dùng trong bộ nhớ.");
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
        public String getMaVe() { return maVe.get(); }
        public String getGaDi() { return gaDi.get(); }
        public String getGaDen() { return gaDen.get(); }
        public String getGioDi() { return gioDi.get(); }
        public double getGia() { return gia.get(); }
        public String getTrangThai() { return trangThai.get(); }
        public void setTrangThai(String trangThai) { this.trangThai.set(trangThai); }
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
        public String getMaTau() { return maTau.get(); }
        public String getTenTau() { return tenTau.get(); }
        public String getLoaiTau() { return loaiTau.get(); }
        public String getSoToa() { return soToa.get(); }
        public String getTrangThai() { return trangThai.get(); }
        public void setTrangThai(String trangThai) { this.trangThai.set(trangThai); }
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
        public String getMaLichTrinh() { return maLichTrinh.get(); }
        public String getMaTau() { return maTau.get(); }
        public String getGaDi() { return gaDi.get(); }
        public String getGaDen() { return gaDen.get(); }
        public String getGioDi() { return gioDi.get(); }
        public String getGioDen() { return gioDen.get(); }
        public String getTrangThai() { return trangThai.get(); }
        public void setTrangThai(String trangThai) { this.trangThai.set(trangThai); }
    }

    public static class NhanVien {
        private final SimpleStringProperty maNV;
        private final SimpleStringProperty hoTen;
        private final SimpleStringProperty cccd;
        private final SimpleStringProperty sdt;
        private final SimpleStringProperty boPhan;
        private final SimpleStringProperty caLam;
        private final SimpleStringProperty trangThai;

        public NhanVien(String maNV, String hoTen, String cccd, String sdt, String boPhan, String caLam, String trangThai) {
            this.maNV = new SimpleStringProperty(maNV);
            this.hoTen = new SimpleStringProperty(hoTen);
            this.cccd = new SimpleStringProperty(cccd);
            this.sdt = new SimpleStringProperty(sdt);
            this.boPhan = new SimpleStringProperty(boPhan);
            this.caLam = new SimpleStringProperty(caLam);
            this.trangThai = new SimpleStringProperty(trangThai);
        }
        public String getMaNV() { return maNV.get(); }
        public String getHoTen() { return hoTen.get(); }
        public String getCCCD() { return cccd.get(); }
        public String getSdt() { return sdt.get(); }
        public String getBoPhan() { return boPhan.get(); }
        public String getCaLam() { return caLam.get(); }
        public String getTrangThai() { return trangThai.get(); }
        public void setTrangThai(String trangThai) { this.trangThai.set(trangThai); }
    }
}
