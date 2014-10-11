package com.myproject.institutions.client;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.myproject.institutions.server.InstitutionManager;
import com.myproject.institutions.server.InstitutionTypeManager;
import com.myproject.institutions.shared.FieldVerifier;
import com.myproject.institutions.shared.Institution;
import com.myproject.institutions.shared.InstitutionBuilder;
import com.myproject.institutions.shared.InstitutionType;
import com.gargoylesoftware.htmlunit.javascript.host.Text;
import com.google.appengine.api.blobstore.BlobstoreServicePb.DeleteBlobRequest;
import com.google.gwt.aria.client.TextboxRole;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.thirdparty.javascript.rhino.head.ast.FunctionNode.Form;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Assign2 implements EntryPoint {

	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL()
			+ "upload";

	private VerticalPanel mainHelper;
	private TabPanel mainTabPanel;
	private VerticalPanel FITypePanel;
	private VerticalPanel FIPanel;
	private FlexTable FITypeList;
	private FlexTable FIList;
	private Button addFIType;
	private Button addFI;
	private Button deleteFIType;
	private Button deleteFI;
	private Button changeFIType;
	private Button changeFI;
	private ScrollPanel FITypeScroll;
	private ScrollPanel FIScroll;
	private FITypeManagerAsync FITypeManager = GWT.create(FITypeManager.class);
	private FIManagerAsync FIManager = GWT.create(FIManager.class);
	private XMLImportExportAsync XMLManager = GWT.create(XMLImportExport.class);
	private int rowIndexForType = -1;
	private int rowIndexForInst = -1;
	private Label infoLabel;
	private VerticalPanel typeAddPanel;
	private VerticalPanel FIAddPanel;
	private HorizontalPanel FITypeSplitPanel;
	private HorizontalPanel FISplitPanel;
	private Label FITypeName;
	private Label FITypeCode;
	private TextBox FITypeCodeText;
	private TextBox FITypeNameText;
	private Label FICode;
	private Label FIName;
	private Label FIAddress;
	private Label FIEmail;
	private Label FIPhone;
	private Label FIFax;
	private Label FIType;
	private Label FIRegDate;
	private TextBox FICodeText;
	private TextBox FINameText;
	private TextBox FIAddressText;
	private TextBox FIPhoneText;
	private TextBox FIEmailText;
	private TextBox FIFaxText;
	private ListBox FITypeChooser;
	private TextBox FIRegDateText;
	private InstitutionType tmpType;
	private boolean FIRegistered;
	private boolean FITypeRegistered;
	private HorizontalPanel xmlImportExportPanel;
	private Button xmlImport;
	private Button xmlExport;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		initializeGUI();
		initializeListeners();

	}

	private void initializeGUI() {

		final FormPanel form = new FormPanel();
		form.setAction(UPLOAD_ACTION_URL);

		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		VerticalPanel panel = new VerticalPanel();
		form.setWidget(panel);

		final TextBox tb = new TextBox();
		tb.setName("textBoxFormElement");
		panel.add(tb);

		FileUpload upload = new FileUpload();
		upload.setName("uploadFormElement");
		panel.add(upload);

		panel.add(new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.submit();
			}
		}));

		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			
			@Override
			public void onSubmit(SubmitEvent event) {
				// This event is fired just before the form is submitted. We can
				// take this opportunity to perform validation.
			}
		});

		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// When the form submission is successfully completed, this
				// event is fired. Assuming the service returned a response of
				// type
				// text/html, we can get the result text here (see the FormPanel
				// documentation for further explanation).
				Window.alert(event.getResults());
			}
		});

		RootPanel.get().add(form);
		addWidgetsToMainPanel();
		fillLists();
	}

	private void initializeListeners() {

		FITypeList.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				rowIndexForType = FITypeList.getCellForEvent(event)
						.getRowIndex();
				String code = FITypeList.getText(rowIndexForType, 0);
				AsyncCallback<InstitutionType> callback = new AsyncCallback<InstitutionType>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(InstitutionType result) {
						FITypeCodeText.setText(result.getCode());
						FITypeNameText.setText(result.getName());
					}

				};
				FITypeManager.getTypeByCode(code, callback);
			}
		});

		FIList.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				rowIndexForInst = FIList.getCellForEvent(event).getRowIndex();
				String code = FIList.getText(rowIndexForInst, 0);
				AsyncCallback<Institution> callback = new AsyncCallback<Institution>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Institution result) {
						FICodeText.setText(result.getCode());
						FINameText.setText(result.getName());
						FIAddressText.setText(result.getAddress());
						FIPhoneText.setText(result.getPhone());
						FIEmailText.setText(result.getEmail());
						FIFaxText.setText(result.getFax());
						for (int i = 0; i < FITypeChooser.getItemCount(); i++) {
							if (FITypeChooser.getItemText(i).equals(
									result.getType().getCode())) {
								FITypeChooser.setSelectedIndex(i);
							}
						}
						if (result.getRegDate() != null) {
							DateTimeFormat myDateTimeFormat = DateTimeFormat
									.getFormat("dd/MM/yyyy");
							FIRegDateText.setText(myDateTimeFormat
									.format(result.getRegDate()));
						} else
							FIRegDateText.setText("");
					}

				};

				FIManager.getInstitutionByCode(code, callback);
			}
		});

		deleteFIType.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (rowIndexForType > 0) {
					String code = FITypeList.getText(rowIndexForType, 0);
					rowIndexForType = -1;
					checkType(code);
				}

			}
		});

		deleteFI.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (rowIndexForInst > 0) {
					String code = FIList.getText(rowIndexForInst, 0);
					FIList.removeRow(rowIndexForInst);
					rowIndexForInst = -1;
					getInstAndDelete(code);
				}
			}
		});

		addFIType.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String message = "";
				if (FITypeCodeText.getText().equals(""))
					message += "You must enter the code!";
				if (FITypeNameText.getText().equals(""))
					message = message
							+ ((message.equals("")) ? "You must enter the name!"
									: "\nYou must enter the name!");
				if (!FieldVerifier.isValidCode(FITypeCodeText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid code!"
									: "\nInvalid code");
				if (!FieldVerifier.isValidName(FITypeNameText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid name!"
									: "\nInvalid name");
				if (message.equals("")) {
					createFIType();
				} else {
					infoLabel.setText(message);
				}
			}
		});

		addFI.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String message = "";
				if (FICodeText.getText().equals(""))
					message += "You must enter the code!";
				if (FINameText.getText().equals(""))
					message = message
							+ ((message.equals("")) ? "You must enter the name!"
									: "\n You must enter the name!");
				if (FIRegDateText.getText().equals(""))
					message = message
							+ ((message.equals("")) ? "You must enter the date!"
									: "\n You must enter the date!");
				if (!FieldVerifier.isValidCode(FICodeText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid code!"
									: "\n Invalid code");
				if (!FieldVerifier.isValidName(FINameText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid name!"
									: "\n Invalid name");
				if (!FieldVerifier.isValidPhone(FIPhoneText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid phone!"
									: "\n Invalid phone");
				if (!FieldVerifier.isValidEmail(FIEmailText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid email!"
									: "\n Invalid email");
				if (!FieldVerifier.isValidFax(FIFaxText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid fax!"
									: "\n Invalid fax");
				if (!FieldVerifier.isValidDate(FIRegDateText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid date!"
									: "\n Invalid date");
				if (message.equals("")) {
					createFI();
				} else {
					infoLabel.setText(message);
				}
			}
		});

		changeFIType.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String message = "";
				if (FITypeCodeText.getText().equals(""))
					message += "You must enter the code!";
				if (FITypeNameText.getText().equals(""))
					message = message
							+ ((message.equals("")) ? "You must enter the name!"
									: "\nYou must enter the name!");
				if (!FieldVerifier.isValidCode(FITypeCodeText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid code!"
									: "\nInvalid code");
				if (!FieldVerifier.isValidName(FITypeNameText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid name!"
									: "\nInvalid name");
				if (message.equals("")) {
					updateFIType();
				} else {
					infoLabel.setText(message);
				}
			}
		});

		changeFI.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String message = "";
				if (FICodeText.getText().equals(""))
					message += "You must enter the code!";
				if (FINameText.getText().equals(""))
					message = message
							+ ((message.equals("")) ? "You must enter the name!"
									: "\n You must enter the name!");
				if (FIRegDateText.getText().equals(""))
					message = message
							+ ((message.equals("")) ? "You must enter the date!"
									: "\n You must enter the date!");
				if (!FieldVerifier.isValidCode(FICodeText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid code!"
									: "\n Invalid code");
				if (!FieldVerifier.isValidName(FINameText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid name!"
									: "\n Invalid name");
				if (!FieldVerifier.isValidPhone(FIPhoneText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid phone!"
									: "\n Invalid phone");
				if (!FieldVerifier.isValidEmail(FIEmailText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid email!"
									: "\n Invalid email");
				if (!FieldVerifier.isValidFax(FIFaxText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid fax!"
									: "\n Invalid fax");
				if (!FieldVerifier.isValidDate(FIRegDateText.getText()))
					message = message
							+ ((message.equals("")) ? "Invalid date!"
									: "\n Invalid date");
				if (message.equals("")) {
					updateFI();
				} else {
					infoLabel.setText(message);
				}
			}
		});

		xmlImport.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Void result) {

					}

				};
				XMLManager.XMLImport(callback);
			}
		});

		xmlExport.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Void result) {
						
					}

				};
				XMLManager.XMLExport(null, callback);
			}
		});

	}

	private void updateFI() {

		getType(FITypeChooser.getItemText(FITypeChooser.getSelectedIndex()), 1);

	}

	private void updateFIType() {
		InstitutionType type = new InstitutionType(FITypeCodeText.getText(),
				FITypeNameText.getText());
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					infoLabel.setText("FI type updated!");
					fillFITypeList();
				}
			}

		};
		FITypeManager.updateType(type, callback);
	}

	private void createFIType() {
		checkFITypeByCode(FITypeCodeText.getText());
	}

	private void createFI() {
		checkFIByCode(FICodeText.getText());
	}

	private void checkFIByCode(String code) {
		AsyncCallback<Institution> callback = new AsyncCallback<Institution>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Institution result) {
				if (result != null) {
					infoLabel
							.setText("FI with such code is already registered!");
					FIRegistered = true;
				} else {
					getType(FITypeChooser.getItemText(FITypeChooser
							.getSelectedIndex()), 0);
				}
			}

		};
		FIManager.getInstitutionByCode(code, callback);
	}

	private void checkFITypeByCode(String code) {
		AsyncCallback<InstitutionType> callback = new AsyncCallback<InstitutionType>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(InstitutionType result) {
				if (result != null) {
					infoLabel
							.setText("FI type with such code is already registered!");
					FITypeRegistered = true;
				} else {
					InstitutionType type = new InstitutionType(
							FITypeCodeText.getText(), FITypeNameText.getText());
					AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}

						@Override
						public void onSuccess(Boolean result) {
							if (result) {
								infoLabel.setText("FI type created!");
								fillFITypeList();
							}
						}

					};
					FITypeManager.createType(type, callback);
				}
			}

		};

		FITypeManager.getTypeByCode(code, callback);
	}

	private void deleteFI(Institution inst) {
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					infoLabel.setText("FI deleted!");
					FIList.removeAllRows();
					FIList.setText(0, 0, "Code");
					FIList.setText(0, 1, "Name");
					FIList.setText(0, 2, "Type");
					fillFIList();
				}
			}

		};
		FIManager.deleteInstitution(inst, callback);
	}

	private void checkType(final String code) {
		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Integer result) {
				if (result == 0) {
					getTypeAndDelete(code);
				} else if (result > 0) {
					infoLabel.setText("Current FI Type with \"Code\" = " + code
							+ " is used and can't be deleted");
				}
			}

		};
		FIManager.checkInstitutionByTypeCode(code, callback);
	}

	private void getTypeAndDelete(String code) {
		AsyncCallback<InstitutionType> callback = new AsyncCallback<InstitutionType>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(InstitutionType result) {
				deleteType(result);
			}

		};
		FITypeManager.getTypeByCode(code, callback);

	}

	private void getType(final String code, final int mode) {
		
		final Institution inst = new Institution(FICodeText.getText(),
				FINameText.getText(), FIAddressText.getText(),
				FIPhoneText.getText(), FIEmailText.getText(),
				FIFaxText.getText(), null, null);
		
		AsyncCallback<Integer> callbackID = new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Integer result) {
					inst.setId(result);
					AsyncCallback<InstitutionType> callback = new AsyncCallback<InstitutionType>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}

						@Override
						public void onSuccess(InstitutionType result) {
							if (result != null) {
								Date regDate = null;
								if (!FIRegDateText.getText().equals("")) {
									try {
										DateTimeFormat myDateTimeFormat = DateTimeFormat
												.getFormat("dd/MM/yyyy");
										regDate = myDateTimeFormat
												.parseStrict(FIRegDateText.getText());
									} catch (IllegalArgumentException e) {
										System.err.println(e);
										e.printStackTrace();
									}
								}

								inst.setRegDate(regDate);
								inst.setType(result);
								
								AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										caught.printStackTrace();
									}

									@Override
									public void onSuccess(Boolean result) {
										if (result) {
											if (mode == 0)
												infoLabel.setText("FI created!");
											else
												infoLabel.setText("FI updated!");
											fillFIList();
										}
									}

								};

								if (mode == 0)
									FIManager.createInstitution(inst, callback);
								else
									FIManager.updateInstitution(inst, callback);
							}
						}

					};
					FITypeManager.getTypeByCode(code, callback);
			}

		};

		if(mode != 0) FIManager.getID(inst.getCode(), callbackID);
		
		

	}

	private void getInstAndDelete(String code) {
		AsyncCallback<Institution> callback = new AsyncCallback<Institution>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Institution result) {
				deleteFI(result);
			}

		};
		FIManager.getInstitutionByCode(code, callback);

	}

	private void deleteType(final InstitutionType type) {
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					infoLabel.setText("FI Type deleted!");
					FITypeList.removeAllRows();
					FITypeList.setText(0, 0, "Code");
					FITypeList.setText(0, 1, "Name");
					fillFITypeList();
				}
			}

		};
		FITypeManager.deleteType(type, callback);
	}

	private void fillLists() {
		fillFITypeList();
		fillFIList();

	}

	private void fillFIList() {
		AsyncCallback<ArrayList<Institution>> callback = new AsyncCallback<ArrayList<Institution>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(ArrayList<Institution> result) {
				updateFIList(result);

			}

		};
		FIManager.getInstitutions(callback);
	}

	private void fillFITypeList() {
		AsyncCallback<ArrayList<InstitutionType>> callback = new AsyncCallback<ArrayList<InstitutionType>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(ArrayList<InstitutionType> result) {
				updateTypeList(result);

			}

		};
		FITypeManager.getTypes(callback);
	}

	private void updateTypeList(ArrayList<InstitutionType> types) {
		int rowCounter = 1;
		FITypeChooser.clear();
		for (InstitutionType type : types) {
			FITypeList.setText(rowCounter, 0, type.getCode());
			FITypeList.setText(rowCounter, 1, type.getName());
			FITypeChooser.addItem(type.getCode());
			rowCounter++;
		}
	}

	private void updateFIList(ArrayList<Institution> institutions) {
		int rowCounter = 1;
		for (Institution inst : institutions) {
			FIList.setText(rowCounter, 0, inst.getCode());
			FIList.setText(rowCounter, 1, inst.getName());
			FIList.setText(rowCounter, 2, inst.getType().getCode());
			rowCounter++;
		}
	}

	private void addWidgetsToMainPanel() {

		mainTabPanel = new TabPanel();
		mainHelper = new VerticalPanel();
		infoLabel = new Label("");
		FITypeScroll = new ScrollPanel();
		FIScroll = new ScrollPanel();
		FITypeScroll.setSize("200px", "350px");
		FIScroll.setSize("200px", "350px");
		addFIType = new Button("add");
		addFI = new Button("add");
		deleteFIType = new Button("delete");
		deleteFI = new Button("delete");
		changeFIType = new Button("change");
		changeFI = new Button("change");
		FITypeList = new FlexTable();
		FIList = new FlexTable();
		FITypePanel = new VerticalPanel();
		FIPanel = new VerticalPanel();
		xmlImportExportPanel = new HorizontalPanel();
		xmlImport = new Button("Import");
		xmlExport = new Button("Export");

		FITypeList.setText(0, 0, "Code");
		FITypeList.setText(0, 1, "Name");
		FITypeScroll.add(FITypeList);
		FITypePanel.add(FITypeScroll);

		FIList.setText(0, 0, "Code");
		FIList.setText(0, 1, "Name");
		FIList.setText(0, 2, "Type");
		FIScroll.add(FIList);
		FIPanel.add(FIScroll);

		HorizontalPanel typeNamePanel = new HorizontalPanel();
		HorizontalPanel typeCodePanel = new HorizontalPanel();
		HorizontalPanel typeButtonPanel = new HorizontalPanel();
		HorizontalPanel FINamePanel = new HorizontalPanel();
		HorizontalPanel FICodePanel = new HorizontalPanel();
		HorizontalPanel FIAddressPanel = new HorizontalPanel();
		HorizontalPanel FIPhonePanel = new HorizontalPanel();
		HorizontalPanel FIEmailPanel = new HorizontalPanel();
		HorizontalPanel FIFaxPanel = new HorizontalPanel();
		HorizontalPanel FIRegDatePanel = new HorizontalPanel();
		HorizontalPanel FITypeChooserPanel = new HorizontalPanel();
		HorizontalPanel FIAddButtonPanel = new HorizontalPanel();

		FITypeCode = new Label("Code:");
		FITypeCodeText = new TextBox();
		FITypeName = new Label("Name:");
		FITypeNameText = new TextBox();
		typeAddPanel = new VerticalPanel();
		FIAddPanel = new VerticalPanel();

		typeNamePanel.add(FITypeName);
		typeNamePanel.add(FITypeNameText);
		typeCodePanel.add(FITypeCode);
		typeCodePanel.add(FITypeCodeText);
		typeButtonPanel.add(addFIType);
		typeButtonPanel.add(changeFIType);
		typeButtonPanel.add(deleteFIType);

		FICode = new Label("Code:");
		FIName = new Label("Name:");
		FIAddress = new Label("Address:");
		FIPhone = new Label("Phone:");
		FIEmail = new Label("Email:");
		FIFax = new Label("Fax:");
		FIType = new Label("Type:");
		FIRegDate = new Label("Date:");

		FICodeText = new TextBox();
		FINameText = new TextBox();
		FIAddressText = new TextBox();
		FIPhoneText = new TextBox();
		FIEmailText = new TextBox();
		FIFaxText = new TextBox();
		FIRegDateText = new TextBox();
		FITypeChooser = new ListBox();

		FINamePanel.add(FIName);
		FINamePanel.add(FINameText);
		FICodePanel.add(FICode);
		FICodePanel.add(FICodeText);
		FIAddressPanel.add(FIAddress);
		FIAddressPanel.add(FIAddressText);
		FIPhonePanel.add(FIPhone);
		FIPhonePanel.add(FIPhoneText);
		FIEmailPanel.add(FIEmail);
		FIEmailPanel.add(FIEmailText);
		FIFaxPanel.add(FIFax);
		FIFaxPanel.add(FIFaxText);
		FITypeChooserPanel.add(FIType);
		FITypeChooserPanel.add(FITypeChooser);
		FIRegDatePanel.add(FIRegDate);
		FIRegDatePanel.add(FIRegDateText);
		FIAddButtonPanel.add(addFI);
		FIAddButtonPanel.add(changeFI);
		FIAddButtonPanel.add(deleteFI);

		typeAddPanel.add(typeCodePanel);
		typeAddPanel.add(typeNamePanel);
		typeAddPanel.add(typeButtonPanel);

		FIAddPanel.add(FICodePanel);
		FIAddPanel.add(FINamePanel);
		FIAddPanel.add(FIAddressPanel);
		FIAddPanel.add(FIPhonePanel);
		FIAddPanel.add(FIEmailPanel);
		FIAddPanel.add(FIFaxPanel);
		FIAddPanel.add(FITypeChooserPanel);
		FIAddPanel.add(FIRegDatePanel);
		FIAddPanel.add(FIAddButtonPanel);

		FITypeSplitPanel = new HorizontalPanel();
		FISplitPanel = new HorizontalPanel();

		FITypeSplitPanel.add(FITypePanel);
		FITypeSplitPanel.add(typeAddPanel);

		FISplitPanel.add(FIPanel);
		FISplitPanel.add(FIAddPanel);

		mainTabPanel.add(FITypeSplitPanel, "FI Types");
		mainTabPanel.add(FISplitPanel, "FI's");
		mainTabPanel.selectTab(0);

		xmlImportExportPanel.add(xmlImport);
		xmlImportExportPanel.add(xmlExport);

		mainHelper.add(infoLabel);
		mainHelper.add(mainTabPanel);
		mainHelper.add(xmlImportExportPanel);

		RootPanel.get("mainPanel").add(mainHelper);

	}
}
