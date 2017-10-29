/**
 * @author Khralovich Dzmitry
 */

var Currency = {
    BYN: 'руб.',
    RUB: 'руб.',
    USD: '$'
}

function showMessage(message) {
    var opts = {
        "closeButton": true,
        "positionClass": "toast-top-right",
        "timeOut": "5000"
    };
    toastr.success(message, null, opts);
}

function showError(message) {
    var opts = {
        "closeButton": true,
        "positionClass": "toast-top-right",
        "timeOut": "5000"
    };
    toastr.error(message, null, opts);
}

$(document).ready(function () {
    //TODO DV popover
    //$('[data-toggle="popover"]').popover({html: true, animation: true, delay: {show: 100, hide: 100}});

    $('select').material_select();

    //TODO DV tooltip
    //$('[data-toggle="tooltip"]').tooltip({animation: true, html: true, delay: {show: 300, hide: 300}});
    $('[data-toggle="tooltip"]').tooltip();

    //$('input.length, textarea.length').characterCounter();

    //$('.carousel').carousel();


    $('.datepicker').pickadate({
        selectMonths: true,
        selectYears: 100,
        clear: '',
        onClose: function () {
            $(document.activeElement).blur();
        }
    });

    $('.phone-format').text(function (i, text) {
        //return text.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
        if (text) {
            return '+' + text;
        } else {
            return '';
        }
    });
});

if ($('#imageCropDialog')) {
    var URL = window.URL || window.webkitURL;
    var $logoFile = $('#uploadLogoFile');
    var $imageCropDialog = $('#imageCropDialog');
    var $imageCrop = $('#imageCrop');

    var mediaResult = null;

    //$('#rotateLeft').click(function () {
    //    $imageCrop.cropper('rotate', -90);
    //});
    //$('#rotateRight').click(function () {
    //    $imageCrop.cropper('rotate', 90);
    //});

    if (URL) {
        $logoFile.change(function () {
            var files = this.files;
            if (files && files.length) {
                var file = files[0];

                var allowed = ["jpeg", "jpg", "png"];
                var found = false;
                allowed.forEach(function (extension) {
                    if (file.type.match('image/' + extension)) {
                        found = true;
                    }
                });
                if (!found) {
                    showError('Поддерживаемые типы картинок: jpeg и png');
                    return;
                }

                $imageCropDialog.modal('show');
                showProgress();

                var formData = new FormData();
                formData.append('image', file);

                $.ajax('/api/upload/image/save', {
                    method: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (data) {

                        hideProgress();

                        $imageCrop.cropper({
                            aspectRatio: $logoFile.attr("data-aspect-ratio"),
                            movable: false,
                            zoomable: false,
                            rotatable: true,
                            scalable: false,
                            background: false,
                            minContainerHeight: 400,
                            minContainerWidth: 500,
                            crop: function (e) {
                            }
                        });

                        mediaResult = data;
                        $imageCrop.cropper('reset').cropper('replace', mediaResult.relativePath);
                        $logoFile.val('');
                    },
                    error: function () {
                        showError('Ошибка загрузки картинки!');
                    }
                });
            }
        });
    }

    $("#confirmCrop").click(function () {
        var formData = new FormData();
        var cropProperty = $imageCrop.cropper("getData");
        for (var key in cropProperty) {
            formData.append(key, cropProperty[key]);
        }
        formData.append('logoMediaId', mediaResult.id);

        showProgress();

        $.ajax('/api/upload/image/crop', {
            method: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                $('#logoMediaId').val(data.id);
                $('#logoPath').val(data.relativePath);
                $('#logoImg').attr("src", data.relativePath);
                $imageCropDialog.modal('hide');
            },
            error: function () {
                showError('Ошибка загрузки картинки!');
                $imageCropDialog.modal('hide');
            }
        });
    });

    function showProgress() {
        $('#imageProgress').show();
        $imageCrop.hide();
        $("#confirmCrop").addClass('disabled');
        $('.cropper-container').hide();
    }

    function hideProgress() {
        $('#imageProgress').hide();
        $imageCrop.show();
        $("#confirmCrop").removeClass('disabled');
    }

}

$("#acceptedAgreement").click(function () {
    $("#submitBtn").attr("disabled", !this.checked);
});


$(".ellipsis").autoEllipsisText({offset: -10});
$(".ellipsis-2").autoEllipsisText({multiline: true, accuracyStep: 2, offset: -10});
